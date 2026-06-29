package com.library.bff.dto;

public record AuthorResponse(
    Long id, String name, 
    String nationality, 
    Integer birthYear) {}
