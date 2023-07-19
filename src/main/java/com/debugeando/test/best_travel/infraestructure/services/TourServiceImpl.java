package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.api.models.request.TourRequest;
import com.debugeando.test.best_travel.api.models.response.TourResponse;
import com.debugeando.test.best_travel.domain.entities.jpa.*;
import com.debugeando.test.best_travel.domain.repositories.jpa.CustomerRepository;
import com.debugeando.test.best_travel.domain.repositories.jpa.FlyRepository;
import com.debugeando.test.best_travel.domain.repositories.jpa.HotelRepository;
import com.debugeando.test.best_travel.domain.repositories.jpa.TourRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ITourService;
import com.debugeando.test.best_travel.infraestructure.helpers.BlackListHelper;
import com.debugeando.test.best_travel.infraestructure.helpers.CustomerHelper;
import com.debugeando.test.best_travel.infraestructure.helpers.TourHelper;
import com.debugeando.test.best_travel.util.emuns.TablesEnum;
import com.debugeando.test.best_travel.util.exceptions.IdNotFoundException;
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
    private final CustomerHelper customerHelper;
    private final TourHelper tourHelper;
    private final BlackListHelper blackListHelper;

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(TablesEnum.tour.name()));
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()->new IdNotFoundException(TablesEnum.tour.name()));
        var fly = this.flyRepository.findById(flyId).orElseThrow(()-> new IdNotFoundException(TablesEnum.fly.name()));
        var ticket = this.tourHelper.createTicket(fly,tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()->new IdNotFoundException(TablesEnum.tour.name()));
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long hotelId, Long tourId, Integer totalDay) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(TablesEnum.tour.name()));
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException(TablesEnum.hotel.name()));
        var reservation = this.tourHelper.createReservation(hotel,tourUpdate.getCustomer(),totalDay);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }

    @Override
    public TourResponse create(TourRequest request) {
        this.blackListHelper.isInBlackListCustomer(request.getCustomerId());
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow(()->new IdNotFoundException(TablesEnum.customer.name()));
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly-> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow(()-> new IdNotFoundException(TablesEnum.fly.name()))));
        var hotels = new HashMap<HotelEntity,Integer>();
        request.getHotels().forEach(hotel-> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(()->new IdNotFoundException(TablesEnum.hotel.name())),hotel.getTotalDay()));
        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights,customer))
                .reservations(this.tourHelper.createReservations(hotels,customer))
                .customer(customer)
                .build();
        var tourSaved = this.tourRepository.save(tourToSave);
        this.customerHelper.incrase(customer.getDni(),TourServiceImpl.class);
        return TourResponse.builder()
                .id(tourSaved.getId())
                .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public TourResponse read(Long aLong) {
        var tourFromDB = this.tourRepository.findById(aLong).orElseThrow(()-> new IdNotFoundException(TablesEnum.tour.name()));
        return TourResponse.builder()
                .id(tourFromDB.getId())
                .reservationIds(tourFromDB.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourFromDB.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void delete(Long aLong) {
        var tourToDelete = this.tourRepository.findById(aLong).orElseThrow(()-> new IdNotFoundException(TablesEnum.tour.name()));
        this.tourRepository.delete(tourToDelete);
    }
}
