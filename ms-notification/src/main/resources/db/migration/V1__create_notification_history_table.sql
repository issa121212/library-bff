CREATE TABLE notification_history (
    id           UUID         PRIMARY KEY,
    recipient    VARCHAR(255) NOT NULL,
    message_type VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    sent_at      TIMESTAMP    NOT NULL
);