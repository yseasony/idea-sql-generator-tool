package org.yseasony.sqlgenerator.children;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

import com.intellij.persistence.database.psi.DbColumnElement;

public class InsertSqlGeneratorAction extends BaseSqlGenerator {

    public InsertSqlGeneratorAction() {
        super("insert sql generator");
    }

    public InsertSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "INSERT";
    }

    @Override
    public String getSqlTemplate() {
        return "INSERT INTO $TABLE_NAME$ ($COLUMN_LIST$) VALUES ($INSERT_VALUES$)";
    }

    public static class NamedParameterSqlGeneratorAction extends InsertSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("insert sql generator (named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getInsertValues() {
                    StringBuilder values = new StringBuilder();
                    List<? extends DbColumnElement> columns = tableInfo.getColumns();
                    for (int i = 0; i < columns.size(); i++) {
                        DbColumnElement columnElement = columns.get(i);
                        if (i != 0) {
                            values.append(",");
                        }
                        values.append(":").append(Util.convertCamelCase(columnElement.getName()));
                    }
                    return values.toString();
                }
            };
        }
    }
}
