package org.yseasony.sqlgenerator;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.intellij.persistence.database.psi.DbColumnElement;
import com.intellij.persistence.database.psi.DbTableElement;

public class TableInfo {

    private final DbTableElement  tableElement;

    private List<DbColumnElement> columns;

    public TableInfo(DbTableElement tableElement) {
        this.tableElement = tableElement;
    }

    public String getTableName() {
        return tableElement.getName();
    }

    public List<? extends DbColumnElement> getColumns() {
        return tableElement.getColumns();
    }

    public List<String> getColumnsName() {
        List<String> columnsName = Lists.newArrayList();
        List<? extends DbColumnElement> columns = tableElement.getColumns();
        for (DbColumnElement column : columns) {
            columnsName.add(column.getName());
        }

        return columnsName;
    }

    public List<DbColumnElement> getPrimaryKeys() {
        List<? extends DbColumnElement> columns = getColumns();
        List<DbColumnElement> primaryKeys = Lists.newArrayList();
        for (DbColumnElement column : columns) {
            if (column.isPrimary()) {
                primaryKeys.add(column);
            }
        }
        return primaryKeys;
    }

    public List<DbColumnElement> getNonPrimaryColumns() {
        List<? extends DbColumnElement> columns = getColumns();
        List<DbColumnElement> primaryKeys = getPrimaryKeys();
        List<DbColumnElement> ret = new ArrayList<DbColumnElement>();
        for (DbColumnElement column : columns) {
            if (!primaryKeys.contains(column)) {
                ret.add(column);
            }
        }
        return ret;
    }
}
