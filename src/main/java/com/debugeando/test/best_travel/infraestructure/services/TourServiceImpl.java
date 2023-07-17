package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.api.models.request.TourRequest;
import com.debugeando.test.best_travel.api.models.response.TourResponse;
import com.debugeando.test.best_travel.domain.entities.*;
import com.debugeando.test.best_travel.domain.repositories.CustomerRepository;
import com.debugeando.test.best_travel.domain.repositories.FlyRepository;
import com.debugeando.test.best_travel.domain.repositories.HotelRepository;
import com.debugeando.test.best_travel.domain.repositories.TourRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ITourService;
import com.debugeando.test.best_travel.infraestructure.helpers.TourHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;

    private final TourHelper tourHelper;

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var fly = this.flyRepository.findById(flyId).orElseThrow();
        var ticket = this.tourHelper.createTicket(fly,tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
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
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly-> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow()));
        var hotels = new HashMap<HotelEntity,Integer>();
        request.getHotels().forEach(hotel-> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(),hotel.getTotalDay()));
        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights,customer))
                .reservations(this.tourHelper.createReservations(hotels,customer))
                .customer(customer)
                .build();
        var tourSaved = this.tourRepository.save(tourToSave);
        return TourResponse.builder()
                .id(tourSaved.getId())
                .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public TourResponse read(Long aLong) {
        var tourFromDB = this.tourRepository.findById(aLong).orElseThrow();
        return TourResponse.builder()
                .id(tourFromDB.getId())
                .reservationIds(tourFromDB.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourFromDB.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void delete(Long aLong) {
        var tourToDelete = this.tourRepository.findById(aLong).orElseThrow();
        this.tourRepository.delete(tourToDelete);
    }
}
