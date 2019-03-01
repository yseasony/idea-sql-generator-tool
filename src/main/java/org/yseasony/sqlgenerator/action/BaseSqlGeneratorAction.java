package org.yseasony.sqlgenerator.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiElement;
import org.yseasony.sqlgenerator.configurable.SqlGeneratorConfigComponent;
import org.yseasony.sqlgenerator.utils.*;

import java.awt.datatransfer.StringSelection;

/**
 * 类BaseSqlGenerator.java
 *
 * @author Damon 2014-04-04 下午1:57
 */
public abstract class BaseSqlGeneratorAction extends AnAction {

    private Formatter formatter = new BasicFormatterImpl();

    public BaseSqlGeneratorAction(String text) {
        super(text);
    }

    protected abstract String getSqlTemplate();

    @Override
    public void actionPerformed(AnActionEvent event) {
        PsiElement[] psiElements = event.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            return;
        }

        StringBuilder sbSql = new StringBuilder();
        for (PsiElement psiElement : psiElements) {
            if (!(psiElement instanceof DbTable)) {
                continue;
            }

            TableInfo tableInfo = new TableInfo((DbTable) psiElement);
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

            sbSql.append(sqlTemplate);

            if (psiElements.length > 1) {
                sbSql.append(";");
            }

            sbSql.append(Util.LF);


        }

        SqlGeneratorConfigComponent.SqlGeneratorConfig sqlGeneratorConfig = SqlGeneratorConfigComponent.getInstance(event.getProject());
        String sql = sbSql.toString();

        if (sqlGeneratorConfig != null && sqlGeneratorConfig.isBeautySqlFormat()) {
            sql = formatter.format(sbSql.toString());
        }

        CopyPasteManager.getInstance().setContents(new StringSelection(sql));
    }

    protected SqlGenerator createSqlGenerator(TableInfo tableInfo) {
        return new SqlGenerator(tableInfo);
    }

    abstract String getStatementType();

}
