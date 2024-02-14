package com.soundstock.model.dto;

import com.soundstock.enums.UserRole;
import com.soundstock.model.entity.OrderEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    List<OrderEntity> orders;
}
