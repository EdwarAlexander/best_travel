package com.debugeando.test.best_travel.infraestructure.abstract_services;

import com.debugeando.test.best_travel.api.models.response.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogoService<HotelResponse> {
    Set<HotelResponse> findByRatingGreaterThan(Integer rating);
}
