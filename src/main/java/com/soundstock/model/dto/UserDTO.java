package com.soundstock.model.dto;

import com.github.javafaker.Stock;
import com.soundstock.enums.UserRole;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class UserDTO {
    Long id;
    String username;
    String email;
    String password;
    boolean enabled;
    UserRole role;
    BigDecimal balance;
    List<OrderDTO> orders;
}
