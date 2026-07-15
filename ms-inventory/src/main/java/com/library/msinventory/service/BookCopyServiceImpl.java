package com.library.msinventory.service;

import lombok.extern.slf4j.Slf4j;

import com.library.msinventory.dto.BookCopyRequest;
import com.library.msinventory.dto.BookCopyResponse;
import com.library.msinventory.exception.BookCopyNotFoundException;
import com.library.msinventory.model.BookCopy;
import com.library.msinventory.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepository repository;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public BookCopyResponse create(BookCopyRequest request) {
        log.info("Ejecutando método create");
        BookCopy entity = BookCopy.builder()
            .id(UUID.randomUUID())
            .bookId(request.bookId())
            .barcode(request.barcode())
            .physicalStatus(request.physicalStatus())
            .isAvailable(true)
            .build();
        return toResponse(repository.save(entity));
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public BookCopyResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new BookCopyNotFoundException(id)));
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<BookCopyResponse> findAll() {
        log.info("Ejecutando método findAll");
        return repository.findAll().stream().map(this::toResponse).toList();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public BookCopyResponse update(UUID id, BookCopyRequest request) {
        log.info("Ejecutando método update");
        BookCopy entity = repository.findById(id)
            .orElseThrow(() -> new BookCopyNotFoundException(id));
        entity.setBookId(request.bookId());
        entity.setBarcode(request.barcode());
        entity.setPhysicalStatus(request.physicalStatus());
        return toResponse(repository.save(entity));
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        BookCopy entity = repository.findById(id)
            .orElseThrow(() -> new BookCopyNotFoundException(id));
        repository.delete(entity);
    }

    private BookCopyResponse toResponse(BookCopy entity) {
        return new BookCopyResponse(entity.getId(), entity.getBookId(), entity.getBarcode(), entity.getPhysicalStatus(), entity.getIsAvailable());
    }

}
