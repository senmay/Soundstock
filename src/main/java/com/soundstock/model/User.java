package com.soundstock.model;

import com.soundstock.enums.UserRole;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isEnabled = false;
    private UserRole role;
}
