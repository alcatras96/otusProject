package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.OwnerDataService;

import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerDataService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public Content<OwnerViewModel> getAll(int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/owners?page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public Optional<OwnerViewModel> getOwnerById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/owners/" + id, Optional.class);
    }

    @Override
    public OwnerViewModel getOwnerByUserId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/owners/user/" + id, OwnerViewModel.class);
    }

    @Override
    public OwnerViewModel saveOwner(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + "/api/owners", owner, OwnerViewModel.class).getBody();
    }

    @Override
    public void saveEditedOwner(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + "/api/owners", owner, OwnerViewModel.class);
    }

    @Override
    public void deleteOwner(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + "/api/owners/" + id);
    }

    @Override
    public OwnerViewModel saveOwnerBa(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + "/api/owners/ba", owner, OwnerViewModel.class).getBody();
    }
}
