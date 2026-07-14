package com.library.msnotification.repository;

import com.library.msnotification.model.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, UUID> {
}
