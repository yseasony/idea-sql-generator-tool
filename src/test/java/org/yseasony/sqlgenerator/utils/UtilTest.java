package org.yseasony.sqlgenerator.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTest {

    @Test
    public void qualifyTableNameWithSchema() {
        assertEquals("mydb.users", Util.qualifyTableName("mydb", "users"));
    }

    @Test
    public void qualifyTableNameWithEmptySchema() {
        assertEquals("users", Util.qualifyTableName("", "users"));
    }

    @Test
    public void qualifyTableNameWithNullSchema() {
        assertEquals("users", Util.qualifyTableName(null, "users"));
    }
}
