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
  id                       int(10)      unsigned NOT NULL AUTO_INCREMENT,
  first_name               varchar(128)          NOT NULL,
  last_name                varchar(128)          NOT NULL DEFAULT '',
  email                    varchar(128)                   DEFAULT  NULL,
  web_role_id              int(10)      unsigned NOT NULL DEFAULT 1,
  last_login_date_utc      datetime              NOT NULL DEFAULT current_timestamp(),
  insert_date_utc          datetime              NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY user_profile_AK1 (email),
  KEY user_profile_IDX1 (web_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE user_profile ADD CONSTRAINT web_role__user_profile__FK2 
FOREIGN KEY (web_role_id) REFERENCES web_role (id) ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- HYDRO-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION