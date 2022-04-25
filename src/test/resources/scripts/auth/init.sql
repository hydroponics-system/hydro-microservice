DELETE FROM user_profile;


INSERT INTO user_profile (id, first_name, last_name, email)
VALUES (1, 'Auth', 'User', 'test@mail.com');


INSERT INTO user_credentials(user_id, password)
VALUES (1, '$2a$10$KusdNWjdceySzNAG3EH8a.5HuIOMWH4hl4Ke64Daqaeqivy1y0Rd.')