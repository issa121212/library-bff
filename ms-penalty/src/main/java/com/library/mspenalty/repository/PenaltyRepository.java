package com.library.mspenalty.repository;

import com.library.mspenalty.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PenaltyRepository extends JpaRepository<Penalty, UUID> {
}
