package com.haptm.chatapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @NotNull
    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User receiver;
}
