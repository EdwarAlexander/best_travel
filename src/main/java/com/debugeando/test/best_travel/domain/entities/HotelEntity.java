package com.debugeando.test.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Entity(name = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String address;

    private Integer rating;

    private BigDecimal price;
}
