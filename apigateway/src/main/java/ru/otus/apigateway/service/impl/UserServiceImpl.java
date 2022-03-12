package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.UserViewModel;
import ru.otus.apigateway.service.api.UserDataService;

import java.util.*;

@Service("usersServiceImpl")
public class UserServiceImpl implements UserDataService, UserDetailsService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/users";

    private final String backendServerUrl;

    public UserServiceImpl(@Value("${backend.server.url}") String backendServerUrl) {
        this.backendServerUrl = backendServerUrl;
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
        UserViewModel user = findByLogin(name);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

    @Override
    public UserViewModel findByLogin(String login) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/login/" + login, UserViewModel.class);
    }

    private Set<GrantedAuthority> getAuthority(UserViewModel user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }
}
