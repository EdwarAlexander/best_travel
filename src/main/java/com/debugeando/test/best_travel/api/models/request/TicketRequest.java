package com.debugeando.test.best_travel.api.models.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketRequest {

    private String idClient;

    private Long idFly;

    @Email(message = "invalid email")
    private String email;
}
