INSERT INTO person(id, name) VALUES (10001,'Hello person 1');
INSERT INTO person(id, name) VALUES (10002,'Hello person 2');
INSERT INTO person(id, name) VALUES (10003,'Hello person 3');

INSERT INTO user (id, first_name, last_name, email, encrypted_password, enabled) VALUES (1, 'Tor', 'Super admin', 'super@gmail.com', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG', TRUE);
INSERT INTO user (id, first_name, last_name, email, encrypted_password, enabled) VALUES (2, 'Adam', 'Admin', 'admin@gmail.com', '$2a$10$HMebjLzQR6sfSlWu1e8uYu5rESTaQx8OlszcxQzNHMOEQxuSm.awm', TRUE);
INSERT INTO user (id, first_name, last_name, email, encrypted_password, enabled) VALUES (3, 'Den', 'Manager', 'manager@gmail.com', '$2a$10$es.7mva9Z/HIsTIJqOcDt.9o1DIzdfts35mKyU5YORDnaTwr9HiyW', TRUE);
INSERT INTO user (id, first_name, last_name, email, encrypted_password, enabled) VALUES (4, 'Vlad', 'User', 'user@gmail.com', '$2a$10$1DZ7g5eE8lQXS/jiNHovju5xl8Pd4LPeEBQ4L3aW784N0cJBvulQW', TRUE);

INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_MANAGER');
INSERT INTO role (id, name) VALUES (3, 'ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 3);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (4, 3);

INSERT INTO password_reset_token (id, expiry_date, token, user_id) VALUES (1, '2017-01-01 00:00:00', 'expired-token', 3);
INSERT INTO password_reset_token (id, expiry_date, token, user_id) VALUES (2, '2222-01-01 00:00:00', 'valid-token', 4);