-- Add the owner_id column to marketplace_item
ALTER TABLE marketplace_item
ADD COLUMN owner_id integer NOT NULL;

-- Add the foreign key constraint
ALTER TABLE marketplace_item
ADD CONSTRAINT fk_marketplace_item_owner FOREIGN KEY (owner_id) REFERENCES "users"(id);