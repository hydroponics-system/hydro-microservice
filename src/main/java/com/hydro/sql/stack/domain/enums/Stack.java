package com.hydro.sql.stack.domain.enums;

/**
 * Enums for a stack environment
 * 
 * @author Sam Butler
 * @since September 6, 2021
 */
public enum Stack {
    DEV("hydro_db_dev"), PROD("hydro_db_prod");

    private String schema;

    Stack(String schema) {
        this.schema = schema;
    }

    /**
     * Get the schema of the enum value for the Stack.
     * 
     * @return {@link String} of the schema of the Stack enum.
     */
    public String getSchema() {
        return schema;
    }
}