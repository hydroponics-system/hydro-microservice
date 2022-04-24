package com.hydro.common.exceptions;

/**
 * Exception for when a SQL fragment is not found in the specified file.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public class SqlFragmentNotFoundException extends BaseException {
    public SqlFragmentNotFoundException(String fragmentName, String fileName) {
        super(String.format("The fragment '%s' was not found in the file '%s'", fragmentName, fileName));
    }
}
