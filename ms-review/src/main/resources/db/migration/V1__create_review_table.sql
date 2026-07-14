CREATE TABLE review (
    id        UUID         PRIMARY KEY,
    book_id   UUID         NOT NULL,
    username  VARCHAR(255) NOT NULL,
    rating    INTEGER      NOT NULL,
    comment   TEXT
);