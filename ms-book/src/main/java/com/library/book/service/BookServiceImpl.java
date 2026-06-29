package com.library.book.service;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.library.book.dto.BookRequest;
import com.library.book.dto.BookResponse;
import com.library.book.exception.BookNotFoundException;
import com.library.book.model.Book;
import com.library.book.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse create(BookRequest request) {
        Book book = Book.builder()
            .title(request.title())
            .authorId(request.authorId())
            .category(request.category())
            .isbn(request.isbn())
            .build();
        return toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse findById(UUID id) {
        return toResponse(bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id)));
    }

    @Override
    public List<BookResponse> findAll(String title, String category) {
        if (title != null && !title.isBlank())
            return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(this::toResponse).toList();
        if (category != null && !category.isBlank())
            return bookRepository.findByCategoryContainingIgnoreCase(category).stream().map(this::toResponse).toList();
        return bookRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public BookResponse update(UUID id, BookRequest request) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        book.setTitle(request.title());
        book.setAuthorId(request.authorId());
        book.setCategory(request.category());
        book.setIsbn(request.isbn());
        return toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(UUID id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthorId(), b.getCategory(), b.getIsbn());
    }
}
