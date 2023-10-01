package com.soundstock.model.entity;

import com.soundstock.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String value;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private Boolean used = false;
    private LocalDateTime expirationDate;
    private String userEmail;
    public LocalDateTime generateExpirationDate() {
        return LocalDateTime.now().plusMonths(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TokenEntity that = (TokenEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

