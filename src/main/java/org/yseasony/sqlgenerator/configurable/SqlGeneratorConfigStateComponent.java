package org.yseasony.sqlgenerator.configurable;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import org.jetbrains.annotations.Nullable;

/**
 * Created by yelei.yl on 2018/2/9.
 */
@State(
        name = "SqlGeneratorConfig",
        storages = @Storage(id = "other", file = StoragePathMacros.WORKSPACE_FILE + "/sqlGenerator.xml")
)
public class SqlGeneratorConfigStateComponent implements PersistentStateComponent<SqlGeneratorConfigState> {

    private SqlGeneratorConfigState sqlGeneratorConfigState = new SqlGeneratorConfigState();

    @Nullable
    @Override
    public SqlGeneratorConfigState getState() {
        return sqlGeneratorConfigState;
    }

    @Override
    public void loadState(SqlGeneratorConfigState sqlGeneratorConfigState) {
        this.sqlGeneratorConfigState = sqlGeneratorConfigState;
    }


}
