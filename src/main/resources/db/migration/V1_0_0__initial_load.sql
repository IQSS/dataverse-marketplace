CREATE SEQUENCE user_id_seq;
CREATE SEQUENCE role_id_seq;

CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL PRIMARY KEY,
    username varchar NOT NULL UNIQUE,
    email varchar NOT NULL UNIQUE,
    password varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id integer NOT NULL PRIMARY KEY,
    role_name varchar NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

insert into users (id, username, email, password) values (nextval('user_id_seq'), 'admin', 'admin@localhost', '$2a$10$ZGbgWue/D39CfwwStB.qWe5hWqvSU8qi4DVZDzKt3ZK3374KSFP8q');
insert into roles (id, role_name) values (nextval('role_id_seq'), 'ADMIN');
insert into user_roles (user_id, role_id) values (1, 1);
