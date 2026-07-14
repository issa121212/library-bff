package com.library.msnotification.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "notification_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationHistory {

    @Id
    private UUID id;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "message_type", nullable = false)
    private String messageType;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "sent_at", nullable = false)
    private java.time.LocalDateTime sentAt;
}
