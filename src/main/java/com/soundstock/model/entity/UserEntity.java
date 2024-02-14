package com.soundstock.model.entity;

import com.soundstock.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled = false;
    private BigDecimal balance;
    @CreationTimestamp
    private LocalDateTime joinDate;
    @LastModifiedDate
    private LocalDateTime modificationDate;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return enabled == that.enabled && id.equals(that.id) && username.equals(that.username) && email.equals(that.email) && password.equals(that.password) && Objects.equals(joinDate, that.joinDate) && Objects.equals(modificationDate, that.modificationDate) && role == that.role;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, enabled, joinDate, modificationDate, role);
    }
}
