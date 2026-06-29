package com.library.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.author.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
