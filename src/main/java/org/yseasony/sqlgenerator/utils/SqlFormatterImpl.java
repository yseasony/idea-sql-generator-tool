package org.yseasony.sqlgenerator.utils;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;

public class SqlFormatterImpl implements Formatter {

    private static final FormatConfig FORMAT_CONFIG = FormatConfig.builder().indent("    ").build();

    /**
     * 标准方言基础上补充两点：MySQL 的 ON DUPLICATE KEY UPDATE 作为顶级子句；保留 :name 命名占位符
     */
    private final SqlFormatter.Formatter delegate = SqlFormatter.extend(cfg -> cfg
            .plusReservedTopLevelWords("ON DUPLICATE KEY UPDATE")
            .plusNamedPlaceholderTypes(":"));

    @Override
    public String format(String source) {
        return delegate.format(source, FORMAT_CONFIG);
    }
}
