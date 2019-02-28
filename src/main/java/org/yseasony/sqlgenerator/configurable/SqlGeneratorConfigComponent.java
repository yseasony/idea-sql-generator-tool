package org.yseasony.sqlgenerator.configurable;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * Created by yelei.yl on 2018/2/9.
 */
@State(
        name = "SqlGeneratorConfigComponent",
        storages = @Storage(file = "sqlGenerator.xml")
)
public class SqlGeneratorConfigComponent implements PersistentStateComponent<SqlGeneratorConfigComponent.SqlGeneratorConfig> {


    public static class SqlGeneratorConfig {

        private String pluginVersion = "";

        private boolean beautySqlFormat;

        public String getPluginVersion() {
            return pluginVersion;
        }

        public void setPluginVersion(String pluginVersion) {
            this.pluginVersion = pluginVersion;
        }

        public boolean isBeautySqlFormat() {
            return beautySqlFormat;
        }

        public void setBeautySqlFormat(boolean beautySqlFormat) {
            this.beautySqlFormat = beautySqlFormat;
        }
    }

    public SqlGeneratorConfigComponent() {
        System.out.println("SqlGeneratorConfigComponent init");
    }

    private SqlGeneratorConfig sqlGeneratorConfig = new SqlGeneratorConfig();

    @Nullable
    @Override
    public SqlGeneratorConfig getState() {
        System.out.println("getState");
        return this.sqlGeneratorConfig;
    }

    @Override
    public void loadState(SqlGeneratorConfig sqlGeneratorConfig) {
        System.out.println("loadState");
        this.sqlGeneratorConfig = sqlGeneratorConfig;
    }

    @Nullable
    public static SqlGeneratorConfig getInstance(Project project) {
        SqlGeneratorConfigComponent sqlGeneratorConfigComponent = ServiceManager.getService(project, SqlGeneratorConfigComponent.class);
        return sqlGeneratorConfigComponent.getSqlGeneratorConfig();
    }


    public SqlGeneratorConfig getSqlGeneratorConfig() {
        return sqlGeneratorConfig;
    }

    public void setSqlGeneratorConfig(SqlGeneratorConfig sqlGeneratorConfig) {
        this.sqlGeneratorConfig = sqlGeneratorConfig;
    }
}
