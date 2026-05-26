package com.techacademy.trainbase.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String username;

    @Email(message = "Email must be valid")
    private String email;

    private String firstName;
    private String lastName;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
