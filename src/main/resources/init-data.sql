INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
INSERT INTO users (id, password, username)  VALUES (1, 'admin', 'admin');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);