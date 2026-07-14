package com.library.msnotification.dto;

import java.util.UUID;

public record NotificationHistoryResponse(
    UUID id,
        String recipient,
        String messageType,
        String content,
        java.time.LocalDateTime sentAt
) {}
