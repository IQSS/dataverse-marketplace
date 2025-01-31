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
