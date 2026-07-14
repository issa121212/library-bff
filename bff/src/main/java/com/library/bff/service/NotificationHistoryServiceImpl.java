package com.library.bff.service;

import com.library.bff.client.NotificationHistoryClient;
import com.library.bff.dto.NotificationHistoryRequest;
import com.library.bff.dto.NotificationHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final NotificationHistoryClient client;

    @Override
    public NotificationHistoryResponse create(NotificationHistoryRequest request) {
        return client.create(request);
    }

    @Override
    public NotificationHistoryResponse findById(UUID id) {
        return client.findById(id);
    }

    @Override
    public List<NotificationHistoryResponse> findAll() {
        return client.findAll();
    }

    @Override
    public NotificationHistoryResponse update(UUID id, NotificationHistoryRequest request) {
        return client.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        client.delete(id);
    }
}
