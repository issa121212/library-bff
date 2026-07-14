package com.library.bff.dto;

import java.util.UUID;

public record BookCopyResponse(
    UUID id,
    UUID bookId,
    String barcode,
    String physicalStatus,
    Boolean isAvailable
) {}
