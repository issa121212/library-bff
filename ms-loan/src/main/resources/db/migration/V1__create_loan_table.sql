CREATE TABLE loan (
    id           UUID         PRIMARY KEY,
    book_copy_id UUID         NOT NULL,
    username     VARCHAR(255) NOT NULL,
    loan_date    TIMESTAMP    NOT NULL,
    due_date     TIMESTAMP    NOT NULL,
    return_date  TIMESTAMP,
    status       VARCHAR(255) NOT NULL
);