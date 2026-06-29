package com.library.bff.dto;

import java.util.UUID;

public record BookWithAuthorResponse(
    UUID   id,
    String title,
    String category,
    String isbn,
    AuthorResponse author
) {}
