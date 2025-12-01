-- ========================================
-- V1__Create_sessions_table.sql
-- Migration for JWT authentication sessions
-- ========================================

-- Sessions table for managing JWT authentication sessions
CREATE TABLE IF NOT EXISTS sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(512) NOT NULL UNIQUE,
    refresh_token VARCHAR(512) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    refresh_expires_at TIMESTAMP NOT NULL,
    user_agent VARCHAR(500),
    ip_address VARCHAR(45),
    is_revoked BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_sessions_user 
        FOREIGN KEY (user_id) 
        REFERENCES users(id) 
        ON DELETE CASCADE
);

-- Indexes for performance optimization
CREATE INDEX IF NOT EXISTS idx_sessions_token ON sessions(token);
CREATE INDEX IF NOT EXISTS idx_sessions_refresh_token ON sessions(refresh_token);
CREATE INDEX IF NOT EXISTS idx_sessions_user_id ON sessions(user_id);
CREATE INDEX IF NOT EXISTS idx_sessions_expires_at ON sessions(expires_at);
CREATE INDEX IF NOT EXISTS idx_sessions_is_revoked ON sessions(is_revoked);

-- Comments for documentation
COMMENT ON TABLE sessions IS 'Stores JWT authentication sessions for users';
COMMENT ON COLUMN sessions.id IS 'Primary key';
COMMENT ON COLUMN sessions.user_id IS 'Foreign key to users table';
COMMENT ON COLUMN sessions.token IS 'JWT access token';
COMMENT ON COLUMN sessions.refresh_token IS 'JWT refresh token for token rotation';
COMMENT ON COLUMN sessions.expires_at IS 'Access token expiration timestamp';
COMMENT ON COLUMN sessions.refresh_expires_at IS 'Refresh token expiration timestamp';
COMMENT ON COLUMN sessions.user_agent IS 'Browser/client user agent string';
COMMENT ON COLUMN sessions.ip_address IS 'Client IP address';
COMMENT ON COLUMN sessions.is_revoked IS 'Whether the session has been manually revoked';
COMMENT ON COLUMN sessions.is_active IS 'Whether the session is still active';
COMMENT ON COLUMN sessions.created_date IS 'Session creation timestamp';
COMMENT ON COLUMN sessions.updated_date IS 'Last update timestamp';
