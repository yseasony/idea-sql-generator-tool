package org.yseasony.sqlgenerator.children;


import com.intellij.database.model.DasColumn;
import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

import java.util.List;

public class UpdateSqlGeneratorAction extends BaseSqlGenerator {

    public UpdateSqlGeneratorAction() {
        super("update sql generator");
    }

    public UpdateSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "UPDATE";
    }

    @Override
    protected String getSqlTemplate() {
        return "UPDATE $TABLE_NAME$ SET $SET_CLAUSE$ $WHERE_CLAUSE$";
    }

    public static class NamedParameterSqlGeneratorAction extends UpdateSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("update sql generator (named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getSetClause() {
                    List<DasColumn> columns = tableInfo.getNonPrimaryColumns();

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < columns.size(); i++) {
                        DasColumn element = columns.get(i);
                        if (i != 0) {
                            sb.append(',');
                        }
                        String columnName = element.getName();
                        sb.append(" ").append(columnName).append(" = :")
                                .append(Util.convertCamelCase(columnName));
                    }
                    return sb.toString();
                }

                @Override
                public String getWhereClause() {
                    return Util.makeNamedWhereClause(tableInfo.getPrimaryKeys());
                }
            };
        }
    }
}
