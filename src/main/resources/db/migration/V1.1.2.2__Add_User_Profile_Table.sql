-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.3__Add_User_Profile_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: HYDRO-2: Create User Profile Table 
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_profile (
    id                     INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name             VARCHAR(128) NOT NULL,
    last_name              VARCHAR(128) NOT NULL DEFAULT '',
    email                  VARCHAR(128) DEFAULT NULL,
    web_role_id            INT(10) UNSIGNED NOT NULL DEFAULT 1,
    last_login_date_utc    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP (),
    insert_date_utc        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP (),
    PRIMARY KEY (id),
    UNIQUE KEY user_profile_AK1 (email),
    KEY web_role__user_profile__FK1 (web_role_id),
    FOREIGN KEY (web_role_id)
        REFERENCES web_role (id)
        ON UPDATE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;


-- ---------------------------------------------------------------------------------
-- HYDRO-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION