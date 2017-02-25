package org.yseasony.sqlgenerator;

import java.util.List;
import java.util.Locale;

public final class Util {

    /**
     * 改行
     */
    public static final String LF = System.getProperty("line.separator");

    private Util() {
    }

    public static String makeWhereClause(List<String> columns) {
        if (columns == null || columns.isEmpty()) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE");
        for (int i = 0, size = columns.size(); i < size; i++) {
            whereClause.append(" ");
            if (i != 0) {
                whereClause.append("AND ");
            }
            whereClause.append(columns.get(i)).append(" = ? ");
        }
        return whereClause.toString();
    }

    public static String makeNamedWhereClause(List<String> columns) {
        if (columns.isEmpty()) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE");
        for (int i = 0, size = columns.size(); i < size; i++) {
            whereClause.append(" ");
            if (i != 0) {
                whereClause.append("AND ");
            }
            whereClause.append(columns.get(i)).append(" = :")
                    .append(convertCamelCase(columns.get(i))).append(" ");
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
