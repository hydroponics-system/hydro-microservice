-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.1__Add_User_Profile_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: HYDRO-2: Create User Profile Table 
-- Version: v1.1.1
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_profile (
  id                       INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name               VARCHAR(128)          NOT NULL,
  last_name                VARCHAR(128)          NOT NULL DEFAULT '',
  email                    VARCHAR(128)                   DEFAULT  NULL,
  web_role                 VARCHAR(128)          NOT NULL DEFAULT 'USER',
  last_login_date_utc      DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  insert_date_utc          DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX user_profile_AK1 ON user_profile(email);

-- ---------------------------------------------------------------------------------
-- HYDRO-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION