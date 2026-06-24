-- Database: bank

CREATE DATABASE bank;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(34) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    
    balance NUMERIC(19,2) NOT NULL DEFAULT 0,
    currency VARCHAR(3) NOT NULL, -- EUR, USD
    
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    version BIGINT DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_accounts_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,

    type VARCHAR(20) NOT NULL,
    -- DEPOSIT, WITHDRAW, TRANSFER

    from_account_id BIGINT,
    to_account_id BIGINT,

    amount NUMERIC(19,2) NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'SUCCESS',
    -- SUCCESS, FAILED, PENDING

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_tx_from_account
        FOREIGN KEY (from_account_id)
        REFERENCES accounts(id),

    CONSTRAINT fk_tx_to_account
        FOREIGN KEY (to_account_id)
        REFERENCES accounts(id)
);