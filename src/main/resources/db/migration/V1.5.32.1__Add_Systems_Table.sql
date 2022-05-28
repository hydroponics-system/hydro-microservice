-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.5.32.1__Add_System_Table.sql
-- Author: Sam Butler
-- Date: May 28, 2022
-- Issue: 
-- Version: v1.5.32
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-4: START
-- ---------------------------------------------------------------------------------

CREATE TABLE systems (
  id                 INT          UNSIGNED NOT NULL AUTO_INCREMENT,
  uuid               VARCHAR(128)          NOT NULL,
  part_number        VARCHAR(128)          NOT NULL,
  name               VARCHAR(128)          NULL,
  insert_date_utc    DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE UNIQUE INDEX systems_AK1 ON systems(uuid);

CREATE UNIQUE INDEX systems_AK2 ON systems(part_number);

-- ---------------------------------------------------------------------------------
-- HYDRO-4: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION