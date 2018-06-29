package org.yseasony.sqlgenerator.configurable;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;

import javax.swing.*;

/**
 * Created by yelei.yl on 2018/1/23.
 */
public class SqlGeneratorConfigGUI {

    private JPanel rootPanel;
    private JCheckBox beautySqlFormatCheckBox;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public SqlGeneratorConfigGUI() {
        this.rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 2, JBUI.emptyInsets(), -1, -1));
        rootPanel.setRequestFocusEnabled(true);

        beautySqlFormatCheckBox = new JCheckBox();
        beautySqlFormatCheckBox.setText("Beauty sql format");
        rootPanel.add(beautySqlFormatCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));


    }

}
