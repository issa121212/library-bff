package com.library.author.service;

import lombok.extern.slf4j.Slf4j;
import com.library.author.dto.AuthorRequest;
import com.library.author.dto.AuthorResponse;
import com.library.author.exception.AuthorNotFoundException;
import com.library.author.model.Author;
import com.library.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    /**
     * Crea un nuevo registro en el sistema.
     */


    @Override
    public AuthorResponse create(AuthorRequest request) {
        log.info("Ejecutando método create");
        Author author = Author.builder()
            .name(request.name())
            .nationality(request.nationality())
            .birthYear(request.birthYear())
            .build();
        return toResponse(authorRepository.save(author));
    }
    /**
     * Busca un registro por su ID único.
     */


    @Override
    public AuthorResponse findById(Long id) {
        log.info("Ejecutando método findById");
        return toResponse(authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id)));
    }
    /**
     * Retorna la lista completa de todos los registros.
     */


    @Override
    public List<AuthorResponse> findAll() {
        log.info("Ejecutando método findAll");
        return authorRepository.findAll().stream().map(this::toResponse).toList();
    }
    /**
     * Actualiza la información de un registro existente.
     */


    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        log.info("Ejecutando método update");
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id));
        author.setName(request.name());
        author.setNationality(request.nationality());
        author.setBirthYear(request.birthYear());
        return toResponse(authorRepository.save(author));
    }
    /**
     * Elimina de forma permanente un registro del sistema.
     */


    @Override
    public void delete(Long id) {
        log.info("Ejecutando método delete");
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id));
        authorRepository.delete(author);
    }

    private AuthorResponse toResponse(Author a) {
        return new AuthorResponse(a.getId(), a.getName(), a.getNationality(), a.getBirthYear());
    }
}
