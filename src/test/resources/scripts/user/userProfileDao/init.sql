DELETE FROM user_profile;


INSERT INTO user_profile (id, first_name, last_name, email, web_role_id)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 1),
(2, 'Bill', 'Tanner', 'billT@mail.com', 1),
(3, 'Fake', 'User', 'Fake123@mail.com', 2);
