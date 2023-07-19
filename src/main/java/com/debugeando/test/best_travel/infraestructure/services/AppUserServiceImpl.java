package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.domain.repositories.mongo.AppUserRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ModifyUserService;
import com.debugeando.test.best_travel.util.exceptions.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements ModifyUserService {

    private AppUserRepository appUserRepository;

    @Override
    public Map<String, Boolean> enable(String username) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(COLLECTION_NAME));
        user.setEnabled(!user.isEnabled());
        var userSaved = this.appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(),userSaved.isEnabled());
    }

    @Override
    public Map<String, List<String>> addRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(COLLECTION_NAME));
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = this.appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(),userSaved.getRole().getGrantedAuthorities());
    }

    @Override
    public Map<String, List<String>> removeRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(COLLECTION_NAME));
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = this.appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(),userSaved.getRole().getGrantedAuthorities());
    }

    private static final String COLLECTION_NAME = "app_user";
}
