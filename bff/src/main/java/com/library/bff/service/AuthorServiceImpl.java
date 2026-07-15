package com.library.bff.service;

import lombok.extern.slf4j.Slf4j;

import com.library.bff.client.AuthorClient;
import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorClient authorClient;
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<AuthorResponse> findAll() {
        log.info("Ejecutando método findAll");
        return authorClient.findAll();
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public AuthorResponse findById(Long id) {
        log.info("Ejecutando método findById");
        return authorClient.findById(id);
    }
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public AuthorResponse create(AuthorRequest request) {
        log.info("Ejecutando método create");
        return authorClient.create(request);
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        log.info("Ejecutando método update");
        return authorClient.update(id, request);
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(Long id) {
        log.info("Ejecutando método delete");
        authorClient.delete(id);
    }
}
