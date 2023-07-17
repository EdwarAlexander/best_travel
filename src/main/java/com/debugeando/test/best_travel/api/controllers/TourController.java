package com.debugeando.test.best_travel.api.controllers;

import com.debugeando.test.best_travel.api.models.request.TourRequest;
import com.debugeando.test.best_travel.api.models.response.TourResponse;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ITourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
public class TourController {

    private ITourService tourService;

    @PostMapping
    public ResponseEntity<TourResponse> post(@RequestBody TourRequest request){
        return ResponseEntity.ok(this.tourService.create(request));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(this.tourService.read(id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> removeTicket(@PathVariable Long tourId, @PathVariable UUID ticketId){
        this.tourService.removeTicket(ticketId,tourId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String,UUID>> postTicket(@PathVariable Long tourId, @PathVariable Long flyId){
        var response = Collections.singletonMap("ticketId",this.tourService.addTicket(flyId,tourId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> removeReservation(@PathVariable Long tourId, @PathVariable UUID reservationId){
        this.tourService.removeReservation(reservationId,tourId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String,UUID>> postReservation(@PathVariable Long tourId, @PathVariable Long hotelId,@RequestParam Integer totalDay){
        var response = Collections.singletonMap("reservationId",this.tourService.addReservation(hotelId,tourId,totalDay));
        return ResponseEntity.ok(response);
    }
}
