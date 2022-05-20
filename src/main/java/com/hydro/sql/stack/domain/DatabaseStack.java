package com.hydro.sql.stack.domain;

import java.util.Date;

import com.hydro.sql.stack.domain.enums.Stack;

/**
 * Database user object used for creating stacks and giving that user access to
 * it.
 * 
 * @author Sam Butler
 * @since May 20, 2022
 */
public class DatabaseStack {

    private String username;

    private Stack stackType;

    private String stackName;

    private Date createdDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Stack getStackType() {
        return stackType;
    }

    public void setStackType(Stack stackType) {
        this.stackType = stackType;
    }

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
