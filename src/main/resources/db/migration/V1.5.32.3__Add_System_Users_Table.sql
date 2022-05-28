-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.5.32.3__Add_System_Users_Table.sql
-- Author: Sam Butler
-- Date: May 28, 2022
-- Issue: 
-- Version: v1.5.32
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-4: START
-- ---------------------------------------------------------------------------------

CREATE TABLE system_users (
  user_id                  INT(10) UNSIGNED NOT NULL,
  system_id                INT(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX system_users_AK1 ON system_users(user_id, system_id);

CREATE INDEX system_users_IDX1 ON system_users(system_id);

ALTER TABLE system_users ADD CONSTRAINT user_profile__system_users__FK1 
  FOREIGN KEY (user_id) REFERENCES user_profile (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

ALTER TABLE system_users ADD CONSTRAINT systems__system_users__FK2 
  FOREIGN KEY (system_id) REFERENCES systems (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------------
-- HYDRO-4: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION
