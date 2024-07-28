package com.haptm.chatapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    @NotNull(message = "Username cannot be null")
    @Size(max = 50)
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Size(max = 100)
    private String email;
}
