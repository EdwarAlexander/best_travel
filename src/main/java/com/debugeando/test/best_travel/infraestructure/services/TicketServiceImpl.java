package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.api.models.request.TicketRequest;
import com.debugeando.test.best_travel.api.models.response.FlyResponse;
import com.debugeando.test.best_travel.api.models.response.TicketResponse;
import com.debugeando.test.best_travel.domain.entities.TicketEntity;
import com.debugeando.test.best_travel.domain.repositories.CustomerRepository;
import com.debugeando.test.best_travel.domain.repositories.FlyRepository;
import com.debugeando.test.best_travel.domain.repositories.TicketRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ITicketService;
import com.debugeando.test.best_travel.infraestructure.helpers.BlackListHelper;
import com.debugeando.test.best_travel.infraestructure.helpers.CustomerHelper;
import com.debugeando.test.best_travel.util.BestTravelUtil;
import com.debugeando.test.best_travel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class TicketServiceImpl implements ITicketService {
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    @Override
    @Transactional
    public TicketResponse create(TicketRequest request) {
        this.blackListHelper.isInBlackListCustomer(request.getIdClient());
        var fly = this.flyRepository.findById(request.getIdFly()).orElseThrow(IllegalStateException::new);
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow(()-> new IdNotFoundException("customer"));
        var ticketToPersist = TicketEntity.builder()
                                            .id(UUID.randomUUID())
                                            .fly(fly)
                                            .customer(customer)
                                            .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                                            .purchaseDate(LocalDate.now())
                                            .arrivalDate(BestTravelUtil.getRandomLatter())
                                            .departureDate(BestTravelUtil.getRandomSoon())
                                            .build();
         var ticketPersisted = this.ticketRepository.save(ticketToPersist);
         log.info("ticket saved with id: {}",ticketPersisted.getId());
         this.customerHelper.incrase(customer.getDni(),TicketServiceImpl.class);
        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("ticket"));
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var ticketToUpdate = this.ticketRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("ticket"));
        var fly = this.flyRepository.findById(request.getIdFly()).orElseThrow(()->new IdNotFoundException("fly"));
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());
        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = this.ticketRepository.findById(uuid).orElseThrow(()-> new IdNotFoundException("ticket"));
        this.ticketRepository.delete(ticketToDelete);
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity,response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(),flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    @Override
    public BigDecimal findPrice(Long idFly) {
        var fly = this.flyRepository.findById(idFly).orElseThrow(()-> new IdNotFoundException("fly"));
        return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));
    }

    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
}
