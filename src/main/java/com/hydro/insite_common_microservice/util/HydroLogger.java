package com.hydro.insite_common_microservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Custom Hydro application logger.
 * 
 * @author Sam Butler
 * @since July 16, 2022
 */
@Service
public class HydroLogger {

    private Logger LOGGER;

    /**
     * Initialize Logger by logger.
     * 
     * @param logger The logger instance to autowire.
     */
    @Autowired
    public HydroLogger(Logger logger) {
        LOGGER = logger;
    }

    /**
     * Initialize Logger by class.
     * 
     * @param clazz The class type for the logger.
     */
    public HydroLogger(Class<?> clazz) {
        LOGGER = LoggerFactory.getLogger(clazz);
    }

    /**
     * Logs a message at the INFO level.
     * 
     * @param msg The message to display to the log tracker.
     */
    public void info(String msg) {
        LOGGER.info(msg);
    }

    /**
     * Prints an INFO log to the terminal.
     * 
     * @param msg  The message to display to the log tracker.
     * @param args What arguments to insert into the message.
     */
    public void info(String msg, Object... args) {
        LOGGER.info(msg, args);
    }

    /**
     * Logs a message at the WARN level.
     * 
     * @param msg The message to display to the log tracker.
     */
    public void warn(String msg) {
        LOGGER.warn(msg);
    }

    /**
     * Logs a message at the WARN level.
     * 
     * @param msg  The message to display to the log tracker.
     * @param args What arguments to insert into the message.
     */
    public void warn(String msg, Object... args) {
        LOGGER.warn(msg, args);
    }

    /**
     * Logs a message at the ERROR level.
     * 
     * @param msg The message to display to the log tracker.
     */
    public void error(String msg) {
        LOGGER.error(msg);
    }

    /**
     * Logs a message at the ERROR level.
     * 
     * @param msg  The message to display to the log tracker.
     * @param args What arguments to insert into the message.
     */
    public void error(String msg, Object... args) {
        LOGGER.error(msg, args);
    }
}
