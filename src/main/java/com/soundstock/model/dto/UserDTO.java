package com.soundstock.model.dto;

import com.soundstock.enums.UserRole;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    Long id;
    String username;
    String email;
    String password;
    boolean enabled;
    UserRole role;
    BigDecimal balance;
}
