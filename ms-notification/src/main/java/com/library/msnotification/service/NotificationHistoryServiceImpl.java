package com.library.msnotification.service;

import com.library.msnotification.dto.NotificationHistoryRequest;
import com.library.msnotification.dto.NotificationHistoryResponse;
import com.library.msnotification.exception.NotificationHistoryNotFoundException;
import com.library.msnotification.model.NotificationHistory;
import com.library.msnotification.repository.NotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final NotificationHistoryRepository repository;

    @Override
    public NotificationHistoryResponse create(NotificationHistoryRequest request) {
        NotificationHistory entity = NotificationHistory.builder()
            .id(UUID.randomUUID())
            .recipient(request.recipient())
            .messageType(request.messageType())
            .content(request.content())
            .sentAt(java.time.LocalDateTime.now())
            .build();
        return toResponse(repository.save(entity));
    }

    @Override
    public NotificationHistoryResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new NotificationHistoryNotFoundException(id)));
    }

    @Override
    public List<NotificationHistoryResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public NotificationHistoryResponse update(UUID id, NotificationHistoryRequest request) {
        NotificationHistory entity = repository.findById(id)
            .orElseThrow(() -> new NotificationHistoryNotFoundException(id));
        entity.setRecipient(request.recipient());
        entity.setMessageType(request.messageType());
        entity.setContent(request.content());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        NotificationHistory entity = repository.findById(id)
            .orElseThrow(() -> new NotificationHistoryNotFoundException(id));
        repository.delete(entity);
    }

    private NotificationHistoryResponse toResponse(NotificationHistory entity) {
        return new NotificationHistoryResponse(entity.getId(), entity.getRecipient(), entity.getMessageType(), entity.getContent(), entity.getSentAt());
    }
}
