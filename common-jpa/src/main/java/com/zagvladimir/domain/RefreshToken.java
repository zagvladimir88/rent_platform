package com.zagvladimir.domain;

import com.zagvladimir.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "refreshtoken")
public class RefreshToken extends AuditingEntity{

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
