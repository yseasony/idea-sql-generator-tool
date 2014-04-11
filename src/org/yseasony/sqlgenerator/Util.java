package org.yseasony.sqlgenerator;

import java.util.List;
import java.util.Locale;

import com.intellij.persistence.database.psi.DbColumnElement;

public final class Util {

    /** 改行 */
    public static final String LF = System.getProperty("line.separator");

    private Util() {
    }

    public static String makeWhereClause(List<DbColumnElement> columns) {
        if (columns == null || columns.isEmpty()) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE");
        for (int i = 0, size = columns.size(); i < size; i++) {
            DbColumnElement column = columns.get(i);
            whereClause.append(" ");
            if (i != 0) {
                whereClause.append("AND ");
            }
            whereClause.append(column.getName()).append(" = ? ");
        }
        return whereClause.toString();
    }

    public static String makeNamedWhereClause(List<DbColumnElement> columns) {
        if (columns.isEmpty()) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE");
        for (int i = 0, size = columns.size(); i < size; i++) {
            DbColumnElement column = columns.get(i);
            whereClause.append(" ");
            if (i != 0) {
                whereClause.append("AND ");
            }
            whereClause.append(column.getName()).append(" = :")
                    .append(convertCamelCase(column.getName())).append(" ");
        }
        return whereClause.toString();
    }

    public static String convertCamelCase(String value) {
        String[] split = value.split("[_-]");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i == 0) {
                ret.append(s.toLowerCase(Locale.ENGLISH));
            } else {
                ret.append(Character.toUpperCase(s.charAt(0)));
                if (s.length() > 1) {
                    ret.append(s.substring(1).toLowerCase(Locale.ENGLISH));
                }
            }
        }
        return ret.toString();
    }
}
