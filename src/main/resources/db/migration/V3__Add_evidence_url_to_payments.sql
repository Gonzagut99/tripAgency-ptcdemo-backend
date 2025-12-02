-- Add evidence_url column to payments table for payment proof attachments
ALTER TABLE payments ADD COLUMN IF NOT EXISTS evidence_url VARCHAR(500);
