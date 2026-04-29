-- Add status column to marketplace_item table for draft/public filtering
ALTER TABLE marketplace_item ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'DRAFT';

-- Create index for faster queries filtering by status
CREATE INDEX idx_marketplace_item_status ON marketplace_item(status);

-- Update existing tools to be PUBLIC (grandfather existing data)
UPDATE marketplace_item SET status = 'PUBLIC';

-- Create settings table for dynamic configuration
CREATE TABLE settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    last_update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default registration enabled setting
INSERT INTO settings (setting_key, setting_value, description)
VALUES ('registration_enabled', 'true', 'Controls whether public user registration is enabled');
