package com.hydro.common.sql;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Datasource builder class for managing and building a datasource instance with
 * the database.
 * 
 * @author Sam Butler
 * @since May 21, 2022
 */
public class DataSourceDbBuilder {

    private final String DRIVER_CLASSNAME = "com.mysql.cj.jdbc.Driver";

    private DriverManagerDataSource source;

    private String dbProperties;

    private String dbUrl;

    /**
     * Private constructor for setting the datasource.
     * 
     * @param s The datasource to be set.
     */
    private DataSourceDbBuilder(DriverManagerDataSource s) {
        this.source = s;
        this.dbProperties = "?";
        this.source.setDriverClassName(DRIVER_CLASSNAME);
    }

    /**
     * Begins the creation and new instance of the datasource.
     *
     * @return {@link DataSourceDbBuilder} instance with the set datasource.
     */
    public static DataSourceDbBuilder create() {
        return new DataSourceDbBuilder(new DriverManagerDataSource());
    }

    /**
     * Begins the creation and the passed in datasource instance.
     *
     * @param s The datasource to be set.
     * @return {@link DataSourceDbBuilder} instance with the set datasource.
     */
    public static DataSourceDbBuilder create(DriverManagerDataSource s) {
        return new DataSourceDbBuilder(s);
    }

    /**
     * Method for setting the url on the datasource to be used to connect.
     * 
     * @param url The url to set.
     * @return The new {@link DataSourceDbBuilder} with the updated url.
     */
    public DataSourceDbBuilder url(String url) {
        this.dbUrl = url;
        return this;
    }

    /**
     * Sets the username on the datasource to login with.
     * 
     * @param username The username to log in with.
     * @return The new {@link DataSourceDbBuilder} with the updated username.
     */
    public DataSourceDbBuilder username(String username) {
        this.source.setUsername(username);
        return this;
    }

    /**
     * Sets the password to use for the datasource
     * 
     * @param password The password to be set.
     * @return The new {@link DataSourceDbBuilder} with the updated password.
     */
    public DataSourceDbBuilder password(String password) {
        this.source.setPassword(password);
        return this;
    }

    /**
     * Sets the schema name to use for the datasource
     * 
     * @param schema The schema to be set.
     * @return The new {@link DataSourceDbBuilder} with the updated schema name.
     */
    public DataSourceDbBuilder schema(String schema) {
        this.source.setSchema(schema);
        return this;
    }

    /**
     * This will set a new database property on the datasource. As long as it
     * doesn't already exist it will add it.
     * 
     * @param key   The key of the property.
     * @param value The value to set.
     * @return {@link DataSourceDbBuilder} instance updated with the new property.
     */
    public DataSourceDbBuilder addProperty(String key, Object value) {
        if (!dbProperties.contains(key)) {
            this.dbProperties += String.format("%s=%s&", key, value.toString());
        }
        return this;
    }

    /**
     * Set the ssl value for the datasource.
     * 
     * @param v The boolean value to enable or disable it.
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder useSSL(boolean v) {
        this.addProperty("useSSL", v);
        return this;
    }

    /**
     * Set the JDBC compliant timezone shift value for the datasource.
     * 
     * @param v The boolean value to enable or disable it.
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder useJDBCCompliantTimezoneShift(boolean v) {
        this.addProperty("useJDBCCompliantTimezoneShift", v);
        return this;
    }

    /**
     * Set the legacy date time code value for the datasource.
     * 
     * @param v The boolean value to enable or disable it.
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder useLegacyDatetimeCode(boolean v) {
        this.addProperty("useLegacyDatetimeCode", v);
        return this;
    }

    /**
     * Set the unicode value for the datasource.
     * 
     * @param v The boolean value to enable or disable it.
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder useUnicode(boolean v) {
        this.addProperty("useUnicode", v);
        return this;
    }

    /**
     * Set the ability to allow multi line queries value for the datasource.
     * 
     * @param v The value to enable or disable it.
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder allowMultiQueries(boolean v) {
        this.addProperty("allowMultiQueries", v);
        return this;
    }

    /**
     * Set the default timezone value.
     * 
     * @param v The string timezone value
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder serverTimezone(String tz) {
        this.addProperty("serverTimezone", tz);
        return this;
    }

    /**
     * Will use the default property values for the datasource to be used when
     * connecting to the database.
     * 
     * @return Updated {@link DataSourceDbBuilder} instance
     */
    public DataSourceDbBuilder useDefaultProperties() {
        this.useSSL(false);
        this.useJDBCCompliantTimezoneShift(true);
        this.useLegacyDatetimeCode(false);
        this.useUnicode(true);
        this.serverTimezone("UTC");
        return this;
    }

    /**
     * Returns the build datasource with the defined properties.
     * 
     * @return {@link DataSource} instance.
     */
    public DataSource build() {
        this.buildManagerSource();
        return DataSourceBuilder.create().driverClassName(DRIVER_CLASSNAME).username(this.source.getUsername())
                .password(this.source.getPassword()).url(this.source.getUrl()).build();
    }

    /**
     * Returns the build DriverManagerDataSource with the defined properties.
     * 
     * @return {@link DriverManagerDataSource} instance.
     */
    public DriverManagerDataSource buildManagerSource() {
        String lastCharacter = this.dbProperties.substring(this.dbProperties.length() - 1);
        if ("&".equals(lastCharacter) || "?".equals(lastCharacter)) {
            this.dbProperties = this.dbProperties.substring(0, this.dbProperties.length() - 1);
        }

        this.source.setUrl(this.dbUrl + this.dbProperties);
        return this.source;
    }
}
