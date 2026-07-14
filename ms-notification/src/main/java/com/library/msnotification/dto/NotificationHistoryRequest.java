package com.library.msnotification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record NotificationHistoryRequest(
    String recipient,
        String messageType,
        String content
) {}
