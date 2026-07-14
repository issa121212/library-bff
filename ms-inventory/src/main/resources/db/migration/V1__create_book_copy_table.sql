CREATE TABLE book_copy (
    id              UUID         PRIMARY KEY,
    book_id         UUID         NOT NULL,
    barcode         VARCHAR(255) NOT NULL UNIQUE,
    physical_status VARCHAR(255) NOT NULL,
    is_available    BOOLEAN      NOT NULL
);