package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "urls")
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShortUrlEntity extends Auditable{

    @Column(length = 2000, unique = true, nullable = false)
    private String key;
    @Column(name = "full_url", columnDefinition = "TEXT", nullable = false)
    private String fullUrl;
    @Column( name = "click_count")
    private Long clickCount;
    @ManyToOne(targetEntity = UserEntity.class, fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
