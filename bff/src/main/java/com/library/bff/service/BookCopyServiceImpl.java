package com.library.bff.service;

import lombok.extern.slf4j.Slf4j;

import com.library.bff.client.BookCopyClient;
import com.library.bff.dto.BookCopyRequest;
import com.library.bff.dto.BookCopyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyClient client;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public BookCopyResponse create(BookCopyRequest request) {
        log.info("Ejecutando método create");
        return client.create(request);
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public BookCopyResponse findById(UUID id) {
        log.info("Ejecutando método findById");
        return client.findById(id);
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<BookCopyResponse> findAll() {
        log.info("Ejecutando método findAll");
        return client.findAll();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public BookCopyResponse update(UUID id, BookCopyRequest request) {
        log.info("Ejecutando método update");
        return client.update(id, request);
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(UUID id) {
        log.info("Ejecutando método delete");
        client.delete(id);
    }
}
