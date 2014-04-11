package org.yseasony.sqlgenerator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yseasony.sqlgenerator.children.DeleteSqlGeneratorAction;
import org.yseasony.sqlgenerator.children.InsertSqlGeneratorAction;
import org.yseasony.sqlgenerator.children.SelectSqlGeneratorAction;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.persistence.database.TableType;
import com.intellij.persistence.database.psi.DbTableElement;
import com.intellij.persistence.database.view.DatabaseView;

/**
 * 类SqlGeneratorAction.java
 * 
 * @author Damon 2014-03-26 下午4:39
 */
public class SqlGeneratorAction extends ActionGroup {

    public SqlGeneratorAction() {
        super("Sql Generator", true);
    }

    @Override
    public void update(AnActionEvent e) {
        DatabaseView view = DatabaseView.DATABASE_VIEW_KEY.getData(e.getDataContext());
        if (view == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        Object[] tables = view.getTreeBuilder().getSelectedElements().toArray();
        boolean hasTable = false;
        for (Object table : tables) {
            if (table instanceof DbTableElement) {
                DbTableElement tableElement = (DbTableElement) table;
                if (tableElement.getTableType() == TableType.TABLE
                        || tableElement.getTableType() == TableType.VIEW) {
                    hasTable = true;
                    break;
                }
            }
        }
        e.getPresentation().setEnabledAndVisible(hasTable);
        super.update(e);
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
        return new AnAction[] { new SelectSqlGeneratorAction(),
                new SelectSqlGeneratorAction.NamedParameterSqlGeneratorAction(),
                new InsertSqlGeneratorAction(),
                new InsertSqlGeneratorAction.NamedParameterSqlGeneratorAction(),
                new DeleteSqlGeneratorAction(),
                new DeleteSqlGeneratorAction.NamedParameterSqlGeneratorAction() };
    }

}
