package com.debugeando.test.best_travel.infraestructure.abstract_services;

import java.util.List;
import java.util.Map;

public interface ModifyUserService {

    Map<String,Boolean> enable(String username);

    Map<String, List<String>> addRole(String username, String role);

    Map<String, List<String>> removeRole(String username, String role);

}
