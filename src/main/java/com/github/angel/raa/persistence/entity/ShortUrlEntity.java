package com.github.angel.raa.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @Column(name = "key", unique = true, nullable = false)
    private String key;
    @Column(name = "full_url", columnDefinition = "TEXT", nullable = false)
    private String fullUrl;
}
