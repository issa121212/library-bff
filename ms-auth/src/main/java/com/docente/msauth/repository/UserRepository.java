package com.docente.msauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docente.msauth.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
