package org.yseasony.sqlgenerator;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * 类SqlComponent.java
 *
 * @author Damon 2014-03-26 下午5:10
 */
public class SqlComponent implements ApplicationComponent {
    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Sql Generator";
    }
}
