package org.yseasony.sqlgenerator;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.intellij.database.psi.DbColumnElement;

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
        List<? extends DbColumnElement> columns = tableInfo.getColumns();
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
        List<? extends DbColumnElement> columns = tableInfo.getNonPrimaryColumns();
        StringBuilder setClause = new StringBuilder();
        for (int i = 0, size = columns.size(); i < size; i++) {
            DbColumnElement columnElement = columns.get(i);
            if (i != 0) {
                setClause.append(",");
            }
            setClause.append(" ").append(columnElement.getName()).append(" = ?");
        }
        return setClause.toString();
    }
}
