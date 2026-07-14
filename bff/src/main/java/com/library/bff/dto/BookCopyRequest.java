package com.library.bff.dto;

import java.util.UUID;

public record BookCopyRequest(
    UUID bookId,
    String barcode,
    String physicalStatus
) {}
