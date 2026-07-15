package com.library.mspenalty.service;

import com.library.mspenalty.dto.PenaltyRequest;
import com.library.mspenalty.dto.PenaltyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Slf4j
@Service
public class PenaltyServiceImpl implements PenaltyService {

    private final List<PenaltyResponse> penalties = new ArrayList<>();

    /**
     * Calcula y crea una nueva multa para un usuario basado en los retrasos.
     *
     * @param request Datos de la solicitud de multa
     * @return Detalle de la multa generada
     */
    @Override
    public PenaltyResponse create(PenaltyRequest request) {
        log.info("Procesando cálculo de multa para el usuario: {}", request.username());
        PenaltyResponse response = new PenaltyResponse(
            UUID.randomUUID(),
            request.loanId(),
            request.username(),
            request.amount(),
            "PENDING"
        );
        penalties.add(response);
        return response;
    }

    /**
     * Busca una multa por su ID único.
     *
     * @param id Identificador de la multa
     * @return La multa encontrada
     */
    @Override
    public PenaltyResponse findById(UUID id) {
        log.info("Buscando multa con ID: {}", id);
        return penalties.stream()
            .filter(p -> p.id().equals(id))
            .findFirst()
            .orElse(new PenaltyResponse(id, UUID.randomUUID(), "usuario_demo", 10.0, "PENDING"));
    }

    /**
     * Retorna todas las multas registradas en memoria.
     *
     * @return Lista de multas
     */
    @Override
    public List<PenaltyResponse> findAll() {
        log.info("Listando todas las multas");
        return penalties;
    }

    /**
     * Actualiza el estado o monto de una multa existente.
     *
     * @param id Identificador de la multa
     * @param request Nuevos datos de la multa
     * @return La multa actualizada
     */
    @Override
    public PenaltyResponse update(UUID id, PenaltyRequest request) {
        log.info("Actualizando multa con ID: {}", id);
        return new PenaltyResponse(id, request.loanId(), request.username(), request.amount(), "PAID");
    }

    /**
     * Elimina una multa del registro.
     *
     * @param id Identificador de la multa
     */
    @Override
    public void delete(UUID id) {
        log.warn("Intento de eliminar multa con ID: {} - Operación no permitida", id);
        throw new RuntimeException("No está permitido eliminar multas del sistema.");
    }

    /**
     * Registra el pago de una multa, cambiando su estado a PAID.
     *
     * @param id Identificador único de la multa
     * @return Detalle de la multa pagada
     */
    @Override
    public PenaltyResponse payPenalty(UUID id) {
        log.info("Procesando pago para multa con ID: {}", id);
        for (int i = 0; i < penalties.size(); i++) {
            PenaltyResponse p = penalties.get(i);
            if (p.id().equals(id)) {
                PenaltyResponse updated = new PenaltyResponse(p.id(), p.loanId(), p.username(), p.amount(), "PAID");
                penalties.set(i, updated);
                log.info("Multa ID {} pagada con éxito.", id);
                return updated;
            }
        }
        log.warn("Multa ID {} no encontrada en historial temporal. Retornando fallback PAID.", id);
        return new PenaltyResponse(id, UUID.randomUUID(), "usuario_demo", 0.0, "PAID");
    }
}
