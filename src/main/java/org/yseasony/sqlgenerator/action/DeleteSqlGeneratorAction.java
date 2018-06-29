package org.yseasony.sqlgenerator.action;

import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.utils.SqlGenerator;
import org.yseasony.sqlgenerator.utils.TableInfo;
import org.yseasony.sqlgenerator.utils.Util;

public class DeleteSqlGeneratorAction extends BaseSqlGeneratorAction {

    public DeleteSqlGeneratorAction() {
        super("delete sql generator");
    }

    public DeleteSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "DELETE";
    }

    @Override
    protected String getSqlTemplate() {
        return "DELETE FROM $TABLE_NAME$ $WHERE_CLAUSE$";
    }

    public static class NamedParameterSqlGeneratorAction extends DeleteSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("delete sql generator (named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                public String getWhereClause() {
                    return Util.makeNamedWhereClause(tableInfo.getPrimaryKeys());
                }
            };
        }
    }
}
