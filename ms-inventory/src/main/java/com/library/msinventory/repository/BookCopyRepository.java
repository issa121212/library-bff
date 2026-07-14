package com.library.msinventory.repository;

import com.library.msinventory.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {
}
