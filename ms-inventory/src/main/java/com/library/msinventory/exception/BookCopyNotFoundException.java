package com.library.msinventory.exception;

import java.util.UUID;

public class BookCopyNotFoundException extends RuntimeException {
    public BookCopyNotFoundException(UUID id) {
        super("BookCopy with ID " + id + " not found");
    }
}
