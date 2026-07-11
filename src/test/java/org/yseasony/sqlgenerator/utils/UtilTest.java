package org.yseasony.sqlgenerator.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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

    @Test
    public void whereClauseWithSingleKeyHasNoTrailingSpace() {
        assertEquals("WHERE id = ?", Util.makeWhereClause(Collections.singletonList("id")));
    }

    @Test
    public void whereClauseWithMultipleKeys() {
        assertEquals("WHERE id = ? AND tenant_id = ?", Util.makeWhereClause(Arrays.asList("id", "tenant_id")));
    }

    @Test
    public void whereClauseWithEmptyKeysIsEmpty() {
        assertEquals("", Util.makeWhereClause(Collections.emptyList()));
    }

    @Test
    public void namedWhereClauseWithSingleKeyHasNoTrailingSpace() {
        assertEquals("WHERE user_id = :userId", Util.makeNamedWhereClause(Collections.singletonList("user_id")));
    }

    @Test
    public void namedWhereClauseWithMultipleKeys() {
        assertEquals("WHERE id = :id AND tenant_id = :tenantId",
                Util.makeNamedWhereClause(Arrays.asList("id", "tenant_id")));
    }
}
