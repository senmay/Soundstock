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

    public TokenEntity(String value, TokenType type, String userEmail) {
        this.value = value;
        this.type = type;
        this.used=false;
        this.expirationDate = LocalDateTime.now().plusMonths(1);
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenEntity that)) return false;
        return id.equals(that.id) && value.equals(that.value) && type == that.type && used.equals(that.used) && Objects.equals(expirationDate, that.expirationDate) && userEmail.equals(that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, type, used, expirationDate, userEmail);
    }
}

