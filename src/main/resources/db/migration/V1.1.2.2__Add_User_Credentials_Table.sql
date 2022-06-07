-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.2__Add_User_Credentials_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: HYDRO-3: Create User Credentials Table 
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-3: START
-- ---------------------------------------------------------------------------------
CREATE TABLE user_credentials (
  user_id                      INT(10)    UNSIGNED NOT NULL,
  password                     BINARY(60)          NOT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE user_credentials ADD CONSTRAINT user_profile__user_credentials__FK1 
  FOREIGN KEY (user_id) REFERENCES user_profile (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- HYDRO-3: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION