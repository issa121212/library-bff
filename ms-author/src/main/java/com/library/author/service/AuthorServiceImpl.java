package com.library.author.service;
import com.library.author.dto.AuthorRequest;
import com.library.author.dto.AuthorResponse;
import com.library.author.exception.AuthorNotFoundException;
import com.library.author.model.Author;
import com.library.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponse create(AuthorRequest request) {
        Author author = Author.builder()
            .name(request.name())
            .nationality(request.nationality())
            .birthYear(request.birthYear())
            .build();
        return toResponse(authorRepository.save(author));
    }

    @Override
    public AuthorResponse findById(Long id) {
        return toResponse(authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id)));
    }

    @Override
    public List<AuthorResponse> findAll() {
        return authorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id));
        author.setName(request.name());
        author.setNationality(request.nationality());
        author.setBirthYear(request.birthYear());
        return toResponse(authorRepository.save(author));
    }

    @Override
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id));
        authorRepository.delete(author);
    }

    private AuthorResponse toResponse(Author a) {
        return new AuthorResponse(a.getId(), a.getName(), a.getNationality(), a.getBirthYear());
    }
}
