package org.yseasony.sqlgenerator.utils;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.intellij.database.model.DasColumn;
import com.intellij.database.util.DasUtil;
import org.apache.commons.lang.StringUtils;

public class SqlGenerator {

    private final TableInfo tableInfo;

    public SqlGenerator(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public String getColumnList() {
        return StringUtils.join(tableInfo.getColumnsName(), ", ");
    }

    public String getWhereClause() {
        return Util.makeWhereClause(tableInfo.getPrimaryKeys());
    }

    public String getInsertValues() {
        List<DasColumn> columns = tableInfo.getColumns();
        StringBuilder columnList = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            if (i != 0) {
                columnList.append(",");
            }
            columnList.append("?");
        }
        return columnList.toString();
    }

    public String getSetClause() {
        List<DasColumn> columns = tableInfo.getNonPrimaryColumns();
        StringBuilder setClause = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            DasColumn columnElement = columns.get(i);
            if (i != 0) {
                setClause.append(",");
            }
            setClause.append(" ").append(columnElement.getName()).append(" = ?");
        }
        return setClause.toString();
    }

    public String getDuplicateSetClause() {
        List<DasColumn> columns = tableInfo.getNonPrimaryColumns();
        List<String> columnList = Lists.newArrayList();
        for (DasColumn column : columns) {
            if (!DasUtil.isIndexColumn(column)) {
                columnList.add(" " + column.getName() + " = ?");
            }
        }

        return Joiner.on(",").join(columnList);
    }
}
