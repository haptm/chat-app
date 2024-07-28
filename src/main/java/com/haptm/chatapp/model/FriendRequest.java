package com.haptm.chatapp.model;

import com.haptm.chatapp.utils.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "status")
    private RequestStatus status;

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
