package org.yseasony.sqlgenerator.utils;

import com.intellij.database.model.DasColumn;
import org.apache.commons.lang.StringUtils;

import java.util.List;

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
}
