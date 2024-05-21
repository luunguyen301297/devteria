package com.devteria.identity.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.devteria.identity.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    String city;
}
