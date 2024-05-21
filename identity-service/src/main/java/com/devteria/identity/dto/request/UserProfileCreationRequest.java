package com.devteria.identity.dto.request;

import com.devteria.identity.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequest {
    String userId;
    String firstName;
    String lastName;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    String city;
}
