CREATE TABLE author (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    birth_year  INT          NOT NULL
);
