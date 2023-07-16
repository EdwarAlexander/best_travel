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
public class ReservationServiceImpl implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    @Override
    @Transactional
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
        return null;
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    private ReservationResponse entityToResponse(ReservationEntity entity){
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }
    private static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
}
