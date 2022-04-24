package com.hydro.app.user.client.domain;

/**
 * Class to create a User Application object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
public class Application {
    private int id;

    private String name;

    private boolean access;

    private boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
