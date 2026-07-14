package com.library.msinventory.service;

import com.library.msinventory.dto.BookCopyRequest;
import com.library.msinventory.dto.BookCopyResponse;
import com.library.msinventory.exception.BookCopyNotFoundException;
import com.library.msinventory.model.BookCopy;
import com.library.msinventory.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepository repository;

    @Override
    public BookCopyResponse create(BookCopyRequest request) {
        BookCopy entity = BookCopy.builder()
            .id(UUID.randomUUID())
            .bookId(request.bookId())
            .barcode(request.barcode())
            .physicalStatus(request.physicalStatus())
            .isAvailable(true)
            .build();
        return toResponse(repository.save(entity));
    }

    @Override
    public BookCopyResponse findById(UUID id) {
        return toResponse(repository.findById(id)
            .orElseThrow(() -> new BookCopyNotFoundException(id)));
    }

    @Override
    public List<BookCopyResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public BookCopyResponse update(UUID id, BookCopyRequest request) {
        BookCopy entity = repository.findById(id)
            .orElseThrow(() -> new BookCopyNotFoundException(id));
        entity.setBookId(request.bookId());
        entity.setBarcode(request.barcode());
        entity.setPhysicalStatus(request.physicalStatus());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        BookCopy entity = repository.findById(id)
            .orElseThrow(() -> new BookCopyNotFoundException(id));
        repository.delete(entity);
    }

    private BookCopyResponse toResponse(BookCopy entity) {
        return new BookCopyResponse(entity.getId(), entity.getBookId(), entity.getBarcode(), entity.getPhysicalStatus(), entity.getIsAvailable());
    }

}
