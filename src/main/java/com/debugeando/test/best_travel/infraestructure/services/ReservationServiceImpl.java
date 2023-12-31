package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.api.models.request.ReservationRequest;
import com.debugeando.test.best_travel.api.models.response.HotelResponse;
import com.debugeando.test.best_travel.api.models.response.ReservationResponse;
import com.debugeando.test.best_travel.domain.entities.jpa.ReservationEntity;
import com.debugeando.test.best_travel.domain.repositories.jpa.CustomerRepository;
import com.debugeando.test.best_travel.domain.repositories.jpa.HotelRepository;
import com.debugeando.test.best_travel.domain.repositories.jpa.ReservationRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.IReservationService;
import com.debugeando.test.best_travel.infraestructure.helpers.BlackListHelper;
import com.debugeando.test.best_travel.infraestructure.helpers.CustomerHelper;
import com.debugeando.test.best_travel.infraestructure.helpers.EmailHelper;
import com.debugeando.test.best_travel.util.emuns.TablesEnum;
import com.debugeando.test.best_travel.util.exceptions.IdNotFoundException;
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
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;
    @Override
    public ReservationResponse create(ReservationRequest request) {
        this.blackListHelper.isInBlackListCustomer(request.getIdClient());
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException(TablesEnum.hotel.name()));
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow(()-> new IdNotFoundException(TablesEnum.customer.name()));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charger_price_percentage)))
                .build();
        var reservationPersisted = this.reservationRepository.save(reservationToPersist);
        this.customerHelper.incrase(customer.getDni(),ReservationServiceImpl.class);
        //if(Objects.nonNull(request.getEmail())) this.emailHelper.sendMail(request.getEmail(), customer.getFullName(), TablesEnum.reservation.name());
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservation = this.reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(TablesEnum.reservation.name()));
        return this.entityToResponse(reservation);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        var reservationFromDB = this.reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(TablesEnum.reservation.name()));
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException(TablesEnum.hotel.name()));
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
        var reservationDelete = this.reservationRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException(TablesEnum.reservation.name()));
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
    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.20);
}
