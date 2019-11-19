package org.yseasony.sqlgenerator.action;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.intellij.database.model.DasColumn;
import com.intellij.database.util.DasUtil;
import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.utils.SqlGenerator;
import org.yseasony.sqlgenerator.utils.TableInfo;
import org.yseasony.sqlgenerator.utils.Util;

/**
 * @author yseasony
 * @date 2019-11-19
 */
public class DuplicateKeyUpdateSqlGeneratorAction extends BaseSqlGeneratorAction {

    public DuplicateKeyUpdateSqlGeneratorAction() {
        super("duplicateKeyUpdate sql generator");
    }

    public DuplicateKeyUpdateSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    protected String getSqlTemplate() {
        return "INSERT INTO $TABLE_NAME$ ($COLUMN_LIST$) VALUES ($INSERT_VALUES$) ON DUPLICATE KEY UPDATE "
            + "$DUPLICATE_SET_CLAUSE$";
    }

    @Override
    String getStatementType() {
        return "DuplicateKeyUpdate";
    }

    public static class NamedParameterSqlGeneratorAction extends DuplicateKeyUpdateSqlGeneratorAction {
        public NamedParameterSqlGeneratorAction() {
            super("duplicateKeyUpdate sql generator (named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getInsertValues() {
                    StringBuilder values = new StringBuilder();
                    List<DasColumn> columns = tableInfo.getColumns();
                    for (int i = 0; i < columns.size(); i++) {
                        DasColumn columnElement = columns.get(i);
                        if (i != 0) {
                            values.append(",");
                        }
                        values.append(":").append(Util.convertCamelCase(columnElement.getName()));
                    }
                    return values.toString();
                }

                @Override
                public String getDuplicateSetClause() {
                    List<DasColumn> columns = tableInfo.getNonPrimaryColumns();
                    List<String> columnList = Lists.newArrayList();
                    for (DasColumn column : columns) {
                        if (!DasUtil.isIndexColumn(column)) {
                            columnList.add(" " + column.getName() + " = :" + column.getName());
                        }
                    }

                    return Joiner.on(",").join(columnList);
                }
            };
        }

    }
}
