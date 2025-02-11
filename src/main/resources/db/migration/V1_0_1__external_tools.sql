CREATE SEQUENCE IF NOT EXISTS mkt_item_id_seq;
CREATE SEQUENCE IF NOT EXISTS mkt_item_image_id_seq;
CREATE SEQUENCE IF NOT EXISTS ext_tool_version_id_seq;


CREATE TABLE IF NOT EXISTS marketplace_item (
    id integer NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS marketplace_item_image (
    id integer NOT NULL PRIMARY KEY,
    mk_item_id integer NOT NULL,
    alt_text varchar NOT NULL,
    image bytea NOT NULL UNIQUE,
    CONSTRAINT fk_item FOREIGN KEY (mk_item_id) REFERENCES marketplace_item (id)
);

CREATE TABLE IF NOT EXISTS external_tool (
    mk_item_id integer NOT NULL PRIMARY KEY,
    CONSTRAINT fk_item FOREIGN KEY (mk_item_id) REFERENCES marketplace_item (id)
);

CREATE TABLE IF NOT EXISTS external_tool_version (
    id integer NOT NULL PRIMARY KEY,
    mk_item_id integer NOT NULL,
    release_note varchar,
    item_version varchar,
    dv_min_version varchar NOT NULL,
    json_data bytea NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (mk_item_id) REFERENCES marketplace_item (id),
    CONSTRAINT unique_mk_item_version UNIQUE (mk_item_id, id)
);

CREATE TABLE IF NOT EXISTS log_mkt_item (
    mkt_item_id integer NOT NULL,
    date_time timestamp NOT NULL,
    user_id bigint NOT NULL,
    operation varchar NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (mkt_item_id) REFERENCES marketplace_item (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS tags (
    id integer NOT NULL PRIMARY KEY,    
    name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS item_tags (
    item_id integer NOT NULL,
    tag_id integer NOT NULL,
    PRIMARY KEY (item_id, tag_id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES marketplace_item (id),
    CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tags (id)
);

