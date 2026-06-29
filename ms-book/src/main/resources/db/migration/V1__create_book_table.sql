CREATE TABLE book (
    id        UUID         PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    author_id BIGINT       NOT NULL,
    category  VARCHAR(255) NOT NULL,
    isbn      VARCHAR(255) NOT NULL UNIQUE
);
