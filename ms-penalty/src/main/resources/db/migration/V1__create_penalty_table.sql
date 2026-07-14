CREATE TABLE penalty (
    id       UUID             PRIMARY KEY,
    loan_id  UUID             NOT NULL,
    username VARCHAR(255)     NOT NULL,
    amount   DOUBLE PRECISION NOT NULL,
    status   VARCHAR(255)     NOT NULL
);