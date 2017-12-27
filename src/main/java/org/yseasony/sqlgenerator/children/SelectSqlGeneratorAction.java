package org.yseasony.sqlgenerator.children;

import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

/**
 * 类SelectSqlGeneratorAction.java
 * 
 * @author Damon 2014-03-26 下午4:41
 */
public class SelectSqlGeneratorAction extends BaseSqlGenerator {

    public SelectSqlGeneratorAction() {
        super("select sql generator");
    }

    public SelectSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    protected String getSqlTemplate() {
        return "SELECT $COLUMN_LIST$ FROM $TABLE_NAME$ $WHERE_CLAUSE$";
    }

    @Override
    String getStatementType() {
        return "SELECT";
    }

    public static class NamedParameterSqlGeneratorAction extends SelectSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("select sql generator (named parameter)");
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
