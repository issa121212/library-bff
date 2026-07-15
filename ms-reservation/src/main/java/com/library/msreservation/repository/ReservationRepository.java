package com.library.msreservation.repository;

import com.library.msreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByBookIdAndUsernameAndStatus(UUID bookId, String username, String status);
}
