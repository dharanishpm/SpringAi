-- Simple Database Setup for RAG System (Beginner-Friendly)
-- Run this script in your PostgreSQL database

-- 1. Enable pgvector extension (required for vector operations)
CREATE EXTENSION IF NOT EXISTS vector;

-- 2. Drop existing table if it exists (to start fresh)
DROP TABLE IF EXISTS documents;

-- 3. Create the documents table with simple structure
CREATE TABLE documents (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    embedding TEXT,  -- Store as text initially to avoid complexity
    source VARCHAR(255)
);

-- 4. Create a simple index for faster searches (optional but recommended)
CREATE INDEX IF NOT EXISTS idx_documents_source ON documents(source);

-- 5. Verify the setup
SELECT 'Database setup complete! You can now use the RAG system.' as status;