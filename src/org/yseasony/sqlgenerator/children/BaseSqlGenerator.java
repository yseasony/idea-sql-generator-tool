package org.yseasony.sqlgenerator.children;

import java.awt.datatransfer.StringSelection;

import org.yseasony.sqlgenerator.SqlGenerator;
import org.yseasony.sqlgenerator.TableInfo;
import org.yseasony.sqlgenerator.Util;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.database.model.TableType;
import com.intellij.database.psi.DbTableElement;
import com.intellij.database.view.DatabaseView;

/**
 * 类BaseSqlGenerator.java
 * 
 * @author Damon 2014-04-04 下午1:57
 */
public abstract class BaseSqlGenerator extends AnAction {

    public BaseSqlGenerator(String text) {
        super(text);
    }

    protected abstract String getSqlTemplate();

    @Override
    public void actionPerformed(AnActionEvent event) {
        DatabaseView view = DatabaseView.DATABASE_VIEW_KEY.getData(event.getDataContext());
        if (view == null) {
            return;
        }

        Object[] tables = view.getTreeBuilder().getSelectedElements().toArray();

        StringBuilder sql = new StringBuilder();
        for (Object table : tables) {
            if (!(table instanceof DbTableElement)
                    || (((DbTableElement) table).getTableType() != TableType.TABLE && ((DbTableElement) table)
                            .getTableType() != TableType.VIEW)) {
                continue;
            }

            TableInfo tableInfo = new TableInfo((DbTableElement) table);
            String sqlTemplate = getSqlTemplate();
            // table name
            sqlTemplate = sqlTemplate.replaceAll("\\$TABLE_NAME\\$", tableInfo.getTableName());

            // column list
            SqlGenerator generator = createSqlGenerator(tableInfo);
            sqlTemplate = sqlTemplate.replaceAll("\\$COLUMN_LIST\\$", generator.getColumnList());

            // where clause
            sqlTemplate = sqlTemplate.replaceAll("\\$WHERE_CLAUSE\\$", generator.getWhereClause());

            // insert values
            sqlTemplate = sqlTemplate
                    .replaceAll("\\$INSERT_VALUES\\$", generator.getInsertValues());

            // set clause
            sqlTemplate = sqlTemplate.replaceAll("\\$SET_CLAUSE\\$", generator.getSetClause());

            sql.append(sqlTemplate);
            sql.append(Util.LF);
        }
        CopyPasteManager.getInstance().setContents(new StringSelection(sql.toString()));
    }

    protected SqlGenerator createSqlGenerator(TableInfo tableInfo) {
        return new SqlGenerator(tableInfo);
    }

    abstract String getStatementType();

}
