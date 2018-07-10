insert into person(id, name) values(10001,'Hello person 1');
insert into person(id, name) values(10002,'Hello person 2');
insert into person(id, name) values(10003,'Hello person 3');

INSERT INTO user (id, first_name, last_name, email, encrypted_password, enabled) VALUES (1, 'Memory', 'Not Found', 'info@memorynotfound.com', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG', TRUE);
INSERT INTO user (id, first_name, last_name, email, encrypted_password, enabled) VALUES (2, 'Memory', 'Not Found', 'no-reply@memorynotfound.com', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG', TRUE);

INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'MANAGER');
INSERT INTO role (id, name) VALUES (3, 'USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

INSERT INTO password_reset_token (id, expiry_date, token, user_id) VALUES (1, '2017-01-01 00:00:00', 'expired-token', 1);
INSERT INTO password_reset_token (id, expiry_date, token, user_id) VALUES (2, '2222-01-01 00:00:00', 'valid-token', 2);