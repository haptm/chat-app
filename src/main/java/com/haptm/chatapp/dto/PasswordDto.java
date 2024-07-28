package com.haptm.chatapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    @NotNull(message = "Password cannot be null")
    private String newPassword;

    @NotNull(message = "Password cannot be null")
    private String currentPassword;
}
