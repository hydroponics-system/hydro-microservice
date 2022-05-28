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
  id                       int(10)      unsigned NOT NULL AUTO_INCREMENT,
  first_name               varchar(128)          NOT NULL,
  last_name                varchar(128)          NOT NULL DEFAULT '',
  email                    varchar(128)                   DEFAULT  NULL,
  web_role                 varchar(128)          NOT NULL DEFAULT 'USER',
  last_login_date_utc      datetime              NOT NULL DEFAULT current_timestamp(),
  insert_date_utc          datetime              NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX user_profile_AK1 ON user_profile(email);

-- ---------------------------------------------------------------------------------
-- HYDRO-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION