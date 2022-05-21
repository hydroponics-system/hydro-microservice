-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.2.1__Add_Web_Role_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: HYDRO-1: Create Web Role Table 
-- Version: v1.1.2
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- HYDRO-1: START
-- ---------------------------------------------------------------------------------

CREATE TABLE web_role (
  id                   int(10)      unsigned NOT NULL AUTO_INCREMENT,
  text_id              varchar(128)          DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO web_role (id, text_id)
VALUES (1, 'USER'), (2, 'DEVELOPER'), (3, 'SYSTEM_ADMIN'),(4, 'ADMIN');

-- ---------------------------------------------------------------------------------
-- HYDRO-1: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION