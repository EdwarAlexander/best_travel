package com.debugeando.test.best_travel.api.models.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest {
    public String customerId;
    @Size(min = 1, message = "Min fligth tour per tour")
    Set<TourFlyRequest> flights;
    Set<TourHotelRequest> hotels;
}
