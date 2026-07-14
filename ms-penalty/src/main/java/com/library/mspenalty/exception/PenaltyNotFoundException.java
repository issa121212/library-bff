package com.library.mspenalty.exception;

import java.util.UUID;

public class PenaltyNotFoundException extends RuntimeException {
    public PenaltyNotFoundException(UUID id) {
        super("Penalty with ID " + id + " not found");
    }
}
