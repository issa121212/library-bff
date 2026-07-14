CREATE TABLE reservation (
    id               UUID         PRIMARY KEY,
    book_id          UUID         NOT NULL,
    username         VARCHAR(255) NOT NULL,
    reservation_date TIMESTAMP    NOT NULL,
    status           VARCHAR(255) NOT NULL
);