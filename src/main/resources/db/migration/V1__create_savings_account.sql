CREATE TABLE savings_account (
                                 id UUID PRIMARY KEY,
                                 account_number VARCHAR(20) NOT NULL UNIQUE,
                                 customer_name VARCHAR(100) NOT NULL,
                                 customer_key VARCHAR(100) NOT NULL,
                                 account_nick_name VARCHAR(30),
                                 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT chk_customer_name_not_blank
                                     CHECK (char_length(trim(customer_name)) > 0),

                                 CONSTRAINT chk_account_nick_name_length
                                     CHECK (
                                         account_nick_name IS NULL
                                             OR char_length(account_nick_name) BETWEEN 5 AND 30
                                         )
);

CREATE INDEX idx_savings_account_customer_key
    ON savings_account (customer_key);