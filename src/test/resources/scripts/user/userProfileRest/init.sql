DELETE FROM user_profile;


INSERT INTO user_profile (id, first_name, last_name, email, web_role)
VALUES 
(1, 'Test', 'User', 'test@mail.com', 'USER'),
(2, 'Bill', 'Tanner', 'billT@mail.com', 'USER'),
(3, 'Fake', 'User', 'Fake123@mail.com', 'DEVELOPER');
