-- Flyway Migration: Create core tables for accounting-service
-- PostgreSQL dialect

CREATE TABLE IF NOT EXISTS vet_salary (
    id          BIGSERIAL PRIMARY KEY,
    vet_id      BIGINT      NOT NULL UNIQUE,
    vet_name    VARCHAR(255) NOT NULL,
    salary      NUMERIC(19,2) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS vet_payment (
    id          BIGSERIAL PRIMARY KEY,
    vet_id      BIGINT        NOT NULL,
    payment_date DATE         NOT NULL,
    amount      NUMERIC(19,2) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_vet_payment_vet_date ON vet_payment(vet_id, payment_date);
