package org.yseasony.sqlgenerator.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BasicFormatterImplTest {

    private static final String LS = System.lineSeparator();

    private final Formatter formatter = new BasicFormatterImpl();

    @Test
    public void formatSelect() {
        String formatted = formatter.format("SELECT id, user_name, email FROM users WHERE id = ?");
        assertEquals(String.join(LS,
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
        assertEquals(String.join(LS,
                "SELECT",
                "    id",
                "FROM",
                "    mydb.users",
                "WHERE",
                "    id = ?"), formatted);
    }

    @Test
    public void formatSelectWithNamedParameter() {
        String formatted = formatter.format("SELECT id FROM users WHERE user_id = :userId");
        assertEquals(String.join(LS,
                "SELECT",
                "    id",
                "FROM",
                "    users",
                "WHERE",
                "    user_id = :userId"), formatted);
    }

    @Test
    public void formatInsertKeepsInsertIntoOnOneLine() {
        String formatted = formatter.format("INSERT INTO users (id, user_name) VALUES (?, ?)");
        assertEquals(String.join(LS,
                "INSERT INTO users",
                "    (id, user_name)",
                "VALUES",
                "    (?, ?)"), formatted);
    }

    @Test
    public void formatUpdate() {
        String formatted = formatter.format("UPDATE users SET user_name = ?, email = ? WHERE id = ?");
        assertEquals(String.join(LS,
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
        assertEquals(String.join(LS,
                "DELETE",
                "FROM",
                "    users",
                "WHERE",
                "    id = ?"), formatted);
    }

    @Test
    public void formatOnDuplicateKeyUpdateAlignsAssignments() {
        String formatted = formatter.format(
                "INSERT INTO users (id, user_name) VALUES (?, ?) ON DUPLICATE KEY UPDATE user_name = ?, email = ?");
        assertEquals(String.join(LS,
                "INSERT INTO users",
                "    (id, user_name)",
                "VALUES",
                "    (?, ?)",
                "ON DUPLICATE KEY UPDATE",
                "    user_name = ?,",
                "    email = ?"), formatted);
    }

    @Test
    public void formatStripsTrailingWhitespace() {
        String formatted = formatter.format("SELECT id, user_name FROM users WHERE id = ? ");
        for (String line : formatted.split(LS, -1)) {
            assertFalse("line has trailing whitespace: [" + line + "]",
                    line.endsWith(" ") || line.endsWith("\t"));
        }
    }
}
