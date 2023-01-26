INSERT INTO users (username, password, enabled) values ('user','user',1);
INSERT INTO authorities (username, authority) values ('user', 'ROLE_USER');

INSERT INTO users (username, password, enabled) values ('admin','admin',1);
INSERT INTO authorities (username, authority) values ('admin', 'ROLE_ADMIN');