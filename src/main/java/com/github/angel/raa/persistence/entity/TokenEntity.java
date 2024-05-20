package com.github.angel.raa.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
@Entity
public class TokenEntity  extends Auditable{

    @Column(unique = true, length = 2000)
    private String token;
    @Column(name = "expiration_date", nullable = false)
    private Date expiration;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
