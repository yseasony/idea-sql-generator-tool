package org.yseasony.sqlgenerator.configurable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by yelei.yl on 2018/1/22.
 */
public class SqlGeneratorConfigPage implements SearchableConfigurable, Configurable.NoScroll {

    private SqlGeneratorConfigGUI configGUI;
    private Project mProject;
    private SqlGeneratorConfigComponent.SqlGeneratorConfig sqlGeneratorConfig;


    public SqlGeneratorConfigPage(Project project) {
        mProject = project;
        sqlGeneratorConfig = SqlGeneratorConfigComponent.getInstance(project);
    }

    @NotNull
    @Override
    public String getId() {
        return getDisplayName();
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "SqlGenerator";
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        configGUI = new SqlGeneratorConfigGUI();
        configGUI.createUI(mProject);
        return configGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return sqlGeneratorConfig.isBeautySqlFormat() != configGUI.isBeautySqlFormat();
    }

    @Override
    public void apply() {
        configGUI.apply();
    }

    private void createUIComponents() {

    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Override
    public void disposeUIResources() {
        configGUI = null;
    }
}
