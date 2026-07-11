package org.yseasony.sqlgenerator.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlFormatterImplTest {

    private final Formatter formatter = new SqlFormatterImpl();

    @Test
    public void formatSelect() {
        String formatted = formatter.format("SELECT id, user_name, email FROM users WHERE id = ?");
        assertEquals(String.join("\n",
                "SELECT",
                "    id,",
                "    user_name,",
                "    email",
                "FROM",
                "    users",
                "WHERE",
                "    id = ?"), formatted);
    }

    @Test
    public void formatSelectWithSchemaQualifiedTable() {
        String formatted = formatter.format("SELECT id FROM mydb.users WHERE id = ?");
        assertEquals(String.join("\n",
                "SELECT",
                "    id",
                "FROM",
                "    mydb.users",
                "WHERE",
                "    id = ?"), formatted);
    }

    @Test
    public void formatSelectWithNamedParametersAndCompositeKey() {
        String formatted = formatter.format("SELECT id FROM users WHERE id = :id AND tenant_id = :tenantId");
        assertEquals(String.join("\n",
                "SELECT",
                "    id",
                "FROM",
                "    users",
                "WHERE",
                "    id = :id",
                "    AND tenant_id = :tenantId"), formatted);
    }

    @Test
    public void formatInsert() {
        String formatted = formatter.format("INSERT INTO users (id, user_name) VALUES (?, ?)");
        assertEquals(String.join("\n",
                "INSERT INTO",
                "    users (id, user_name)",
                "VALUES",
                "    (?, ?)"), formatted);
    }

    @Test
    public void formatInsertWithNamedParameters() {
        String formatted = formatter.format("INSERT INTO users (id, user_name) VALUES (:id, :userName)");
        assertEquals(String.join("\n",
                "INSERT INTO",
                "    users (id, user_name)",
                "VALUES",
                "    (:id, :userName)"), formatted);
    }

    @Test
    public void formatUpdate() {
        String formatted = formatter.format("UPDATE users SET user_name = ?, email = ? WHERE id = ?");
        assertEquals(String.join("\n",
                "UPDATE",
                "    users",
                "SET",
                "    user_name = ?,",
                "    email = ?",
                "WHERE",
                "    id = ?"), formatted);
    }

    @Test
    public void formatDelete() {
        String formatted = formatter.format("DELETE FROM users WHERE id = ?");
        assertEquals(String.join("\n",
                "DELETE FROM",
                "    users",
                "WHERE",
                "    id = ?"), formatted);
    }

    @Test
    public void formatOnDuplicateKeyUpdateAsTopLevelClause() {
        String formatted = formatter.format(
                "INSERT INTO users (id, user_name) VALUES (?, ?) ON DUPLICATE KEY UPDATE user_name = ?, email = ?");
        assertEquals(String.join("\n",
                "INSERT INTO",
                "    users (id, user_name)",
                "VALUES",
                "    (?, ?)",
                "ON DUPLICATE KEY UPDATE",
                "    user_name = ?,",
                "    email = ?"), formatted);
    }
}
