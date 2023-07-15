package com.debugeando.test.best_travel.domain.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerEntity {

    @Id
    private String dni;

    @Column(length = 50)
    private String fullName;

    @Column(length = 20)
    private String creditCard;

    @Column(length = 20)
    private String phoneNumber;

    private Integer totalFlights;

    private Integer totalLodgings;

    private Integer totalTours;
}
