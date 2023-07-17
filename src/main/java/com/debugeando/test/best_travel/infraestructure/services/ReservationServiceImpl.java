package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.api.models.request.ReservationRequest;
import com.debugeando.test.best_travel.api.models.response.HotelResponse;
import com.debugeando.test.best_travel.api.models.response.ReservationResponse;
import com.debugeando.test.best_travel.domain.entities.ReservationEntity;
import com.debugeando.test.best_travel.domain.repositories.CustomerRepository;
import com.debugeando.test.best_travel.domain.repositories.HotelRepository;
import com.debugeando.test.best_travel.domain.repositories.ReservationRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    @Override
    public ReservationResponse create(ReservationRequest request) {
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(this.charger_price_percentage)))
                .build();
        var reservationPersisted = this.reservationRepository.save(reservationToPersist);
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservation = this.reservationRepository.findById(uuid).orElseThrow();
        return this.entityToResponse(reservation);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        var reservationFromDB = this.reservationRepository.findById(uuid).orElseThrow();
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();
        reservationFromDB.setHotel(hotel);
        reservationFromDB.setTotalDays(request.getTotalDays());
        reservationFromDB.setDateTimeReservation(LocalDateTime.now());
        reservationFromDB.setDateStart(LocalDate.now());
        reservationFromDB.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationFromDB.setPrice(hotel.getPrice());
        var reservationUpdated = this.reservationRepository.save(reservationFromDB);
        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var reservationDelete = this.reservationRepository.findById(uuid).orElseThrow();
        this.reservationRepository.delete(reservationDelete);
    }

    private ReservationResponse entityToResponse(ReservationEntity entity){
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }
    private static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.20);
}
