-- Add currency column to payments table with default value PEN
ALTER TABLE payments
ADD COLUMN IF NOT EXISTS currency VARCHAR(3) DEFAULT 'PEN' NOT NULL;

-- Update any existing records that might have NULL currency
UPDATE payments SET currency = 'PEN' WHERE currency IS NULL;
