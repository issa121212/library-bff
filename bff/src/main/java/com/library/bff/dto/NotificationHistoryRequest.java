package com.library.bff.dto;

import java.util.UUID;

public record NotificationHistoryRequest(
    String recipient,
    String messageType,
    String content
) {}
