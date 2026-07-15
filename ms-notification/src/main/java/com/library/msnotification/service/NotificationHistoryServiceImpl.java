package com.library.msnotification.service;

import com.library.msnotification.dto.NotificationHistoryRequest;
import com.library.msnotification.dto.NotificationHistoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Slf4j
@Service
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final List<NotificationHistoryResponse> notifications = new ArrayList<>();

    /**
     * Procesa y simula el envío de una notificación (email/sms) a un destinatario.
     *
     * @param request Datos de la notificación a enviar
     * @return Registro histórico de la notificación enviada
     */
    @Override
    public NotificationHistoryResponse create(NotificationHistoryRequest request) {
        log.info("Enviando notificación '{}' a: {}", request.messageType(), request.recipient());
        log.info("Contenido del mensaje: {}", request.content());
        NotificationHistoryResponse response = new NotificationHistoryResponse(
            UUID.randomUUID(),
            request.recipient(),
            request.messageType(),
            request.content(),
            LocalDateTime.now()
        );
        notifications.add(response);
        return response;
    }

    /**
     * Busca una notificación en el historial de envíos.
     *
     * @param id Identificador de la notificación
     * @return Registro de la notificación
     */
    @Override
    public NotificationHistoryResponse findById(UUID id) {
        log.info("Consultando historial de notificación con ID: {}", id);
        return notifications.stream()
            .filter(n -> n.id().equals(id))
            .findFirst()
            .orElse(new NotificationHistoryResponse(id, "ejemplo@correo.com", "EMAIL", "Contenido demo", LocalDateTime.now()));
    }

    /**
     * Retorna todo el historial de notificaciones enviadas.
     *
     * @return Lista histórica de notificaciones
     */
    @Override
    public List<NotificationHistoryResponse> findAll() {
        log.info("Listando historial de notificaciones");
        return notifications;
    }

    /**
     * Actualiza el registro de una notificación.
     *
     * @param id Identificador
     * @param request Nuevos datos
     * @return Registro actualizado
     */
    @Override
    public NotificationHistoryResponse update(UUID id, NotificationHistoryRequest request) {
        log.info("Actualizando registro de notificación con ID: {}", id);
        return new NotificationHistoryResponse(id, request.recipient(), request.messageType(), request.content(), LocalDateTime.now());
    }

    /**
     * Elimina una notificación del historial.
     *
     * @param id Identificador
     */
    @Override
    public void delete(UUID id) {
        log.info("Eliminando del historial notificación con ID: {}", id);
        notifications.removeIf(n -> n.id().equals(id));
    }
}
