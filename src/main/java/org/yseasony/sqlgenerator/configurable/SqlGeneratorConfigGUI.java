package org.yseasony.sqlgenerator.configurable;

import com.intellij.openapi.project.Project;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;

import javax.swing.*;

/**
 * Created by yelei.yl on 2018/1/23.
 */
public class SqlGeneratorConfigGUI {

    private SqlGeneratorConfigComponent.SqlGeneratorConfig sqlGeneratorConfig;
    private JPanel rootPanel;
    private JCheckBox beautySqlFormatCheckBox;
    private boolean beautySqlFormat;

    public void createUI(Project project) {
        sqlGeneratorConfig = SqlGeneratorConfigComponent.getInstance(project);
        if (sqlGeneratorConfig == null) {
            sqlGeneratorConfig = new SqlGeneratorConfigComponent.SqlGeneratorConfig();
        }

        beautySqlFormat = sqlGeneratorConfig.isBeautySqlFormat();
        if (sqlGeneratorConfig.isBeautySqlFormat()) {
            beautySqlFormatCheckBox.setSelected(true);
        } else {
            beautySqlFormatCheckBox.setSelected(false);
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public SqlGeneratorConfigGUI() {
        this.rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 2, JBUI.emptyInsets(), -1, -1));
        rootPanel.setRequestFocusEnabled(true);

        beautySqlFormatCheckBox = new JCheckBox();
        beautySqlFormatCheckBox.setText("Auto SQL formatter");
        beautySqlFormatCheckBox.addActionListener(e -> {
            if (beautySqlFormatCheckBox.isSelected()) {
                beautySqlFormat = true;
            } else {
                beautySqlFormat = false;
            }
        });

        rootPanel.add(beautySqlFormatCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    public void apply() {
        sqlGeneratorConfig.setBeautySqlFormat(beautySqlFormat);
    }

    public boolean isBeautySqlFormat() {
        return beautySqlFormat;
    }
}
