package com.haptm.chatapp.dto;

import com.haptm.chatapp.model.Message;
import com.haptm.chatapp.model.Notification;
import com.haptm.chatapp.model.User;
import com.haptm.chatapp.utils.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;

    @NotNull
    @Size(max = 50)
    private String username;

    @NotNull
    @Size(max = 100)
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private Set<User> friends;
}
