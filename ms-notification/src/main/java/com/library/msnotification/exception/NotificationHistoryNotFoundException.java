package com.library.msnotification.exception;

import java.util.UUID;

public class NotificationHistoryNotFoundException extends RuntimeException {
    public NotificationHistoryNotFoundException(UUID id) {
        super("NotificationHistory with ID " + id + " not found");
    }
}
