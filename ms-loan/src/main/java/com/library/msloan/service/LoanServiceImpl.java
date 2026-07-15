package com.library.msloan.service;

import lombok.extern.slf4j.Slf4j;

import com.library.msloan.dto.LoanRequest;
import com.library.msloan.dto.LoanResponse;
import com.library.msloan.exception.LoanNotFoundException;
import com.library.msloan.model.Loan;
import com.library.msloan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public LoanResponse create(LoanRequest request) {
        log.info("Ejecutando método create");
        Loan entity = Loan.builder()
            .id(UUID.randomUUID())
            .bookCopyId(request.bookCopyId())
            .username(request.username())
            .loanDate(java.time.LocalDateTime.now())
            .dueDate(java.time.LocalDateTime.now().plusDays(14))
            .returnDate(null)
            .status("ACTIVE")
            .build();
        return toResponse(repository.save(entity));
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public LoanResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id)));
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<LoanResponse> findAll() {
        log.info("Ejecutando método findAll");
        return repository.findAll().stream().map(this::toResponse).toList();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public LoanResponse update(UUID id, LoanRequest request) {
        log.info("Ejecutando método update");
        Loan entity = repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id));
        entity.setBookCopyId(request.bookCopyId());
        entity.setUsername(request.username());
        return toResponse(repository.save(entity));
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        Loan entity = repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id));
        repository.delete(entity);
    }

    /**
     * Procesa la devolución de un libro, marcando el préstamo como RETURNED.
     *
     * @param id Identificador único del préstamo
     * @return El préstamo devuelto y actualizado
     */
    @Override
    public LoanResponse returnLoan(UUID id) {
        log.info("Procesando devolución para préstamo con ID: {}", id);
        Loan entity = repository.findById(id)
            .orElseThrow(() -> new LoanNotFoundException(id));
        entity.setStatus("RETURNED");
        entity.setReturnDate(java.time.LocalDateTime.now());
        return toResponse(repository.save(entity));
    }

    private LoanResponse toResponse(Loan entity) {
        return new LoanResponse(entity.getId(), entity.getBookCopyId(), entity.getUsername(), entity.getLoanDate(), entity.getDueDate(), entity.getReturnDate(), entity.getStatus());
    }
}
