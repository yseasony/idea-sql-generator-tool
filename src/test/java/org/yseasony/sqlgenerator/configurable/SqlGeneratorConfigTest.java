package org.yseasony.sqlgenerator.configurable;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class SqlGeneratorConfigTest {

    @Test
    public void useSchemaPrefixDefaultsToFalse() {
        assertFalse(new SqlGeneratorConfigComponent.SqlGeneratorConfig().isUseSchemaPrefix());
    }
}
