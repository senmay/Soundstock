package com.soundstock.model.entity;

import com.soundstock.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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
    private boolean isEnabled = false;
    @CreationTimestamp
    private LocalDateTime joinDate;
    @LastModifiedDate
    private LocalDateTime modificationDate;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return isEnabled == that.isEnabled && id.equals(that.id) && username.equals(that.username) && email.equals(that.email) && password.equals(that.password) && Objects.equals(joinDate, that.joinDate) && Objects.equals(modificationDate, that.modificationDate) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, isEnabled, joinDate, modificationDate, role);
    }
}