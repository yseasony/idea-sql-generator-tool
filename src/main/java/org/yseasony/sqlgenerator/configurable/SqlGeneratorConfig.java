package org.yseasony.sqlgenerator.configurable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by yelei.yl on 2018/1/22.
 */
public class SqlGeneratorConfig implements SearchableConfigurable, Configurable.NoScroll {

    private SqlGeneratorConfigGUI configGUI;


    @NotNull
    @Override
    public String getId() {
        return "sqlGeneratorConfig";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Sql Generator";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        configGUI = new SqlGeneratorConfigGUI();
        return configGUI.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

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
