package com.debugeando.test.best_travel.infraestructure.helpers;

import com.debugeando.test.best_travel.domain.repositories.jpa.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@AllArgsConstructor
public class CustomerHelper {

    private final CustomerRepository customerRepository;

    public void incrase(String customerId, Class<?> type){
        var customerToUpdate = this.customerRepository.findById(customerId).orElseThrow();
        switch (type.getSimpleName()){
            case "TourServiceImpl"-> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours()+1);
            case "TicketServiceImpl"-> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights()+1);
            case "ReservationServiceImpl"-> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings()+1);
        }
        this.customerRepository.save(customerToUpdate);
    }
}
