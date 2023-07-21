package com.debugeando.test.best_travel.infraestructure.services;

import com.debugeando.test.best_travel.domain.entities.documents.AppUserDocument;
import com.debugeando.test.best_travel.domain.repositories.mongo.AppUserRepository;
import com.debugeando.test.best_travel.infraestructure.abstract_services.ModifyUserService;
import com.debugeando.test.best_travel.util.exceptions.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements ModifyUserService, UserDetailsService {

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

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        var user = this.appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(COLLECTION_NAME));
        return this.mapUserToDetails(user);
    }

    private static UserDetails mapUserToDetails(AppUserDocument user){
        Set<GrantedAuthority> authorities = user.getRole()
                .getGrantedAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new User(user.getUsername(),
                        user.getPassword(),
                        user.isEnabled(),
                true,
                true,
                true,authorities);
    }
}
