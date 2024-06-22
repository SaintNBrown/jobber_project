package com.jobber.jobs.dto;

import com.jobber.jobs.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignUpDTO {
    private String fullName;
    private String email;
    private String username;
    private String password;
}
