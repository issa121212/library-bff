package com.library.author.dto;

public record AuthorResponse(
    Long id, 
    String name, 
    String nationality, 
    Integer birthYear) {}
