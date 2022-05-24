-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.4.29.1__Add_Grow_Chamber_History_Table.sql
-- Author: Josue Van Dyke
-- Date: May 23, 2022
-- Issue: 
-- Version: v1.4.29
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-4: START
-- ---------------------------------------------------------------------------------

DELETE TABLE grow_chamber_log;

CREATE TABLE grow_chamber_history (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  system_id INT(10) UNSIGNED NOT NULL,
  ph FLOAT(2,2) UNSIGNED NOT NULL DEFAULT 0.0,
  tds FLOAT(3,2) UNSIGNED NOT NULL DEFAULT 0.0,
  water_temp_celsius FLOAT(3,2) UNSIGNED NOT NULL DEFAULT 0.0,
  air_temp_celsius FLOAT(3,2) UNSIGNED NOT NULL DEFAULT 0.0,
  humidity FLOAT(3,2) UNSIGNED NOT NULL DEFAULT 0.0,
  light_status TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
  insert_date_utc DATETIME NOT NULL DEFAULT current_timestamp(3),
  PRIMARY KEY (id));

-- ---------------------------------------------------------------------------------
-- HYDRO-4: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION