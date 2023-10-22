package com.soundstock.model;

import com.soundstock.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private UserRole role;
}
