package com.soundstock.model.dto;

import com.soundstock.enums.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    Long id;
    String username;
    String email;
    String password;
    boolean enabled;
    UserRole role;
}
