package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.UserViewModel;
import ru.otus.apigateway.service.api.UserService;

import java.util.*;

@Service("usersServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/users";

    private final String backendServerUrl;
    private final UserService self;

    public UserServiceImpl(@Value("${backend.server.url}") String backendServerUrl, @Lazy UserService self) {
        this.backendServerUrl = backendServerUrl;
        this.self = self;
    }

    @Override
    public List<UserViewModel> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        UserViewModel[] userViewModelsResponse = restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, UserViewModel[].class);
        return userViewModelsResponse == null ? Collections.emptyList() : Arrays.asList(userViewModelsResponse);
    }

    @Override
    public Optional<UserViewModel> getUserById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id, Optional.class);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserViewModel user = self.getUserByLogin(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Cannot found user by login [%s]", name));
        }
        return new User(
                user.getLogin(),
                user.getPassword(),
                Set.of(new SimpleGrantedAuthority(user.getRole().getName()))
        );
    }

    @Cacheable(value = "usersCache", sync = true)
    @Override
    public UserViewModel getUserByLogin(String login) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/login/" + login, UserViewModel.class);
    }

    @Override
    public UserViewModel getCurrentUserByLogin() {
        return self.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
