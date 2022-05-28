-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.3__Add_Grow_Chamber_Log_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: HYDRO-4: Add Grow Chamber Log Table
-- Version: v1.1.3
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-4: START
-- ---------------------------------------------------------------------------------

CREATE TABLE grow_chamber_log (
  id                           int(11)    unsigned NOT NULL,
  tier                         int(11)    unsigned           DEFAULT NULL,
  pH                           float(4,2) unsigned           DEFAULT NULL,
  tds                          float(4,2) unsigned           DEFAULT NULL,
  water_temp                   float(4,2) unsigned           DEFAULT NULL,
  ambient_temp                 float(4,2) unsigned           DEFAULT NULL,
  humidity_level               int(11)    unsigned           DEFAULT NULL,
  light_status_flag            tinyint(1) unsigned NOT NULL  DEFAULT 0,
  insert_date_utc              datetime            NOT NULL  DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY id_AK1 (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- HYDRO-4: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION