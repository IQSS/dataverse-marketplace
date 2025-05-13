CREATE SEQUENCE query_parameter_id_seq;
CREATE SEQUENCE external_tool_type_id_seq;
CREATE SEQUENCE content_type_id_seq;

ALTER TABLE external_tool_manifest
ADD CONSTRAINT unique_manifest_id UNIQUE (manifest_id);

CREATE TABLE IF NOT EXISTS query_parameter (
    id integer NOT NULL PRIMARY KEY,
    key varchar NOT NULL,
    value varchar NOT NULL,
    manifest_id integer NOT NULL,    
    CONSTRAINT fk_manifest FOREIGN KEY (manifest_id) REFERENCES external_tool_manifest (manifest_id)
);

CREATE TABLE IF NOT EXISTS content_type (
    id integer NOT NULL PRIMARY KEY,
    content_type varchar NOT NULL,
    manifest_id integer NOT NULL,    
    CONSTRAINT fk_manifest FOREIGN KEY (manifest_id) REFERENCES external_tool_manifest (manifest_id)
);

CREATE TABLE IF NOT EXISTS external_tool_type (
    id integer NOT NULL PRIMARY KEY,
    type varchar NOT NULL,
    manifest_id integer NOT NULL,    
    CONSTRAINT fk_manifest FOREIGN KEY (manifest_id) REFERENCES external_tool_manifest (manifest_id)
);


-- add columns
ALTER TABLE external_tool_manifest
ADD COLUMN display_name varchar, 
ADD COLUMN description varchar,
ADD COLUMN scope varchar,
ADD COLUMN tool_url varchar,
ADD COLUMN tool_name varchar,
ADD COLUMN http_method varchar;

ALTER TABLE external_tool_manifest
ALTER COLUMN manifest_stored_resource_id DROP NOT NULL;