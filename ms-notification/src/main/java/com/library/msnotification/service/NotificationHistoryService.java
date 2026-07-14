package com.library.msnotification.service;

import com.library.msnotification.dto.NotificationHistoryRequest;
import com.library.msnotification.dto.NotificationHistoryResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationHistoryService {
    NotificationHistoryResponse create(NotificationHistoryRequest request);
    NotificationHistoryResponse findById(UUID id);
    List<NotificationHistoryResponse> findAll();
    NotificationHistoryResponse update(UUID id, NotificationHistoryRequest request);
    void delete(UUID id);
}
