package org.yseasony.sqlgenerator.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys;
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

    private Formatter formatter = new SqlFormatterImpl();

    public BaseSqlGeneratorAction(String text) {
        super(text);
    }

    protected abstract String getSqlTemplate();

    @Override
    public void actionPerformed(AnActionEvent event) {
        PsiElement[] psiElements = event.getData(PlatformCoreDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            return;
        }

        SqlGeneratorConfigComponent.SqlGeneratorConfig sqlGeneratorConfig = SqlGeneratorConfigComponent.getInstance(event.getProject());
        boolean useSchemaPrefix = sqlGeneratorConfig != null && sqlGeneratorConfig.isUseSchemaPrefix();
        boolean beautySqlFormat = sqlGeneratorConfig != null && sqlGeneratorConfig.isBeautySqlFormat();

        StringBuilder sbSql = new StringBuilder();
        for (PsiElement psiElement : psiElements) {
            if (!(psiElement instanceof DbTable)) {
                continue;
            }

            TableInfo tableInfo = new TableInfo((DbTable) psiElement);
            String sqlTemplate = getSqlTemplate();
            // table name
            sqlTemplate = sqlTemplate.replaceAll("\\$TABLE_NAME\\$", tableInfo.getTableName(useSchemaPrefix));

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

            sqlTemplate = sqlTemplate.replaceAll("\\$DUPLICATE_SET_CLAUSE\\$", generator.getDuplicateSetClause());

            // format each statement separately: the formatter has no notion of ";" separators
            String statement = sqlTemplate.trim();
            if (beautySqlFormat) {
                statement = formatter.format(statement);
            }
            sbSql.append(statement);

            if (psiElements.length > 1) {
                sbSql.append(";");
            }

            sbSql.append(Util.LF);
        }

        CopyPasteManager.getInstance().setContents(new StringSelection(sbSql.toString()));
    }

    protected SqlGenerator createSqlGenerator(TableInfo tableInfo) {
        return new SqlGenerator(tableInfo);
    }

    abstract String getStatementType();

}
