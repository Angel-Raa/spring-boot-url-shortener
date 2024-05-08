package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credential")
@JsonIgnoreProperties(value = {"password"} , allowGetters = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CredentialEntity extends Auditable{
    private String password;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = UserEntity.class, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    private UserEntity user;
}
