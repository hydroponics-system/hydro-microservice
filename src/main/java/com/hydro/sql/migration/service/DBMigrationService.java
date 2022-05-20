package com.hydro.sql.migration.service;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.hydro.common.exceptions.BaseException;
import com.hydro.sql.migration.dao.DBMigrationDao;
import com.hydro.sql.migration.domain.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing the db migration of scripts.
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Transactional
@Service
public class DBMigrationService {

    private final String SCRIPT_LOCATION = "./src/main/resources/db/migration";
    private final Logger LOGGER = LoggerFactory.getLogger(DBMigrationService.class);

    @Autowired
    private DBMigrationDao dao;

    /**
     * This will migrate all sql scripts starting from the passed in version. Any
     * scripts that have a version greater than or equal to the passed in version
     * will get run on the active environment database.
     * 
     * @param version The version to run the scripts off of.
     */
    public void migrateSqlScripts(String version) {
        LOGGER.info("Beginning SQL script migration from version '{}'...", version);
        Version v = new Version(version);

        for (File file : getMigrationScripts().listFiles()) {
            if (versionCompare(v, file.getName()) >= 0) {
                executeScript(file);
            }
        }
        LOGGER.info("SQL script migration complete!");
    }

    /**
     * This will run a sql script matching the passed in sql version. The format
     * will be <MAJOR>.<MINOR>.<FIX>.<BUILD>
     * 
     * @param sqlVersion The version of the sql script to run.
     */
    public void migrateSingleScript(String sqlVersion) throws Exception {
        Version v = new Version(sqlVersion);
        List<File> files = Arrays.asList(getMigrationScripts().listFiles());
        Optional<File> foundFile = files.stream().filter(f -> f.getName().contains(String.format("V%s__", v.get())))
                .findFirst();

        if (foundFile.isPresent()) {
            executeScript(foundFile.get());
        } else {
            throw new BaseException("Could not find file that matches sql script version '" + sqlVersion + "'");
        }
    }

    /**
     * Helper method that will run the content in the passed in file as a sql script
     * against the database.
     * 
     * @param f The file to pull the content from.
     */
    private void executeScript(File f) {
        try {
            String content = Files.readString(f.toPath());
            dao.executeScript(content);
            LOGGER.info("Successfully ran SQL script '{}'", f.getName());
        } catch (Exception e) {
            LOGGER.warn("Error running SQL script '{}'", f.getName());
        }
    }

    /**
     * Helper method for getting the {@link File} instance of the sql scripts. This
     * will return the location and files contained in the db/migration location.
     * 
     * @return {@link File} instance of the scripts location.
     */
    private File getMigrationScripts() {
        return new File(SCRIPT_LOCATION);
    }

    /**
     * Helper method for determining if the sql script version is greater than (1),
     * less than (-1), or equal too (0) the passed in version.
     * 
     * @param versionToCompare The version to check the sql script against.
     * @param sqlScriptName    The script to pull the version from.
     * @return Intger value determing the status of the sql script against the
     *         version to compare.
     */
    private int versionCompare(Version versionToCompare, String sqlScriptName) {
        Version scriptVersion = new Version(sqlScriptName);
        return scriptVersion.compareTo(versionToCompare);
    }
}
