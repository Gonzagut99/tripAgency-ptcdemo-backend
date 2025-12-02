-- V4: Add title, type, and reference_type columns to notifications table
ALTER TABLE notifications 
ADD COLUMN IF NOT EXISTS title VARCHAR(200);

ALTER TABLE notifications 
ADD COLUMN IF NOT EXISTS type VARCHAR(50) DEFAULT 'SYSTEM_INFO';

ALTER TABLE notifications 
ADD COLUMN IF NOT EXISTS reference_type VARCHAR(50);

-- Set type as NOT NULL after setting default values for existing rows
UPDATE notifications SET type = 'SYSTEM_INFO' WHERE type IS NULL;
ALTER TABLE notifications ALTER COLUMN type SET NOT NULL;
