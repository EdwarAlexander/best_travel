package com.debugeando.test.best_travel.infraestructure.abstract_services;

import com.debugeando.test.best_travel.api.models.request.TicketRequest;
import com.debugeando.test.best_travel.api.models.response.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudServices<TicketRequest, TicketResponse, UUID>{
}
