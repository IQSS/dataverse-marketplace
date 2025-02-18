CREATE SEQUENCE IF NOT EXISTS mkt_item_id_seq;
CREATE SEQUENCE IF NOT EXISTS mkt_item_image_id_seq;
CREATE SEQUENCE IF NOT EXISTS version_metadata_id_seq;
CREATE SEQUENCE IF NOT EXISTS resource_storage_id_seq;
CREATE SEQUENCE IF NOT EXISTS db_storage_id_seq;

CREATE TABLE IF NOT EXISTS marketplace_item (
    id integer NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    description varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS external_tool (
    mkt_item_id integer NOT NULL PRIMARY KEY,
    CONSTRAINT fk_item FOREIGN KEY (mkt_item_id) REFERENCES marketplace_item (id)
);

CREATE TABLE IF NOT EXISTS version_metadata (
    id integer NOT NULL PRIMARY KEY,
    release_note varchar,
    item_version varchar,
    dv_min_version varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS external_tool_version (
    mkt_item_id integer NOT NULL,
    id integer NOT NULL,
    v_metadata_id integer NOT NULL,
    PRIMARY KEY (id, mkt_item_id),
    CONSTRAINT fk_item FOREIGN KEY (mkt_item_id) REFERENCES marketplace_item (id),
    CONSTRAINT fk_metadata FOREIGN KEY (v_metadata_id) REFERENCES version_metadata (id)
);

CREATE TABLE IF NOT EXISTS log_mkt_item (
    mkt_item_id integer NOT NULL,
    date_time timestamp NOT NULL,
    user_id bigint NOT NULL,
    operation varchar NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (mkt_item_id) REFERENCES marketplace_item (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS stored_resource_storage_type (
    id integer NOT NULL PRIMARY KEY,
    type_name varchar NOT NULL
);

Insert into stored_resource_storage_type (id, type_name) values (1, 'DATABASE');
Insert into stored_resource_storage_type (id, type_name) values (2, 'FILESYSTEM');
Insert into stored_resource_storage_type (id, type_name) values (3, 'CLOUD_S3');

CREATE TABLE IF NOT EXISTS stored_resource (
    id bigint NOT NULL PRIMARY KEY,
    storage_type_id integer NOT NULL,
    file_name varchar NOT NULL,
    mime_type varchar NOT NULL,
    file_size bigint NOT NULL,
    CONSTRAINT fk_storage_type FOREIGN KEY (storage_type_id) REFERENCES stored_resource_storage_type (id)
);

CREATE TABLE IF NOT EXISTS marketplace_item_image (
    id integer NOT NULL PRIMARY KEY,
    mkt_item_id integer NOT NULL,
    alt_text varchar NOT NULL,
    image_stored_resource_id bigint NOT NULL,
    CONSTRAINT fk_item FOREIGN KEY (mkt_item_id) REFERENCES marketplace_item (id),
    CONSTRAINT fk_image FOREIGN KEY (image_stored_resource_id) REFERENCES stored_resource (id)
);

CREATE TABLE IF NOT EXISTS external_tool_manifest (
    mkt_item_id integer NOT NULL,
    version_id integer NOT NULL,
    manifest_id integer NOT NULL,
    mime_type  varchar NOT NULL,
    manifest_stored_resource_id bigint NOT NULL,
    PRIMARY KEY (version_id, mkt_item_id, manifest_id),
    CONSTRAINT fk_item FOREIGN KEY (version_id, mkt_item_id) REFERENCES external_tool_version (id, mkt_item_id),
    CONSTRAINT fk_manifest FOREIGN KEY (manifest_stored_resource_id) REFERENCES stored_resource (id)
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

CREATE TABLE IF NOT EXISTS database_resource_storage (   
    stored_resource_id bigint NOT NULL PRIMARY KEY,
    resource_data bytea NOT NULL,
    CONSTRAINT fk_resource FOREIGN KEY (stored_resource_id) REFERENCES stored_resource (id)

);

