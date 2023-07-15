package com.debugeando.test.best_travel.infraestructure.abstract_services;

public interface CrudServices<RQ,RS,ID> {
    RS create(RQ request);
    RS read(ID id);
    RS update(RQ request, ID id);
    void delete(ID id);
}
