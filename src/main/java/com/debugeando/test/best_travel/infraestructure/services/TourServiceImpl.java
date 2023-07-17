package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.api.models.request.TourRequest;
import com.debugeando.test.best_travel.api.models.response.TourResponse;
import com.debugeando.test.best_travel.domain.repositories.CustomerRepository;
import com.debugeando.test.best_travel.domain.repositories.FlyRepository;
import com.debugeando.test.best_travel.domain.repositories.HotelRepository;
import com.debugeando.test.best_travel.domain.repositories.TourRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ITourService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@AllArgsConstructor
@Service
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long reservationId, Long tourId) {
        return null;
    }

    @Override
    public TourResponse create(TourRequest request) {
        return null;
    }

    @Override
    public TourResponse read(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
