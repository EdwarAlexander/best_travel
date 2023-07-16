package com.debugeando.test.best_travel.infraestructure.abstract_services;

import com.debugeando.test.best_travel.api.models.request.ReservationRequest;
import com.debugeando.test.best_travel.api.models.response.ReservationResponse;

import java.util.UUID;

public interface IReservationService extends CrudServices<ReservationRequest, ReservationResponse, UUID> {
}
