package com.library.book.dto;

import java.util.UUID;

public record BookResponse(
    UUID id, 
    String title, 
    Long authorId, String 
    category, 
    String isbn) {}
