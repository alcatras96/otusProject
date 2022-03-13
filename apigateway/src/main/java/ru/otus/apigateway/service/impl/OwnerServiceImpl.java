package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.OwnerService;

import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/owners";

    private final String backendServerUrl;
    private final CacheManager cacheManager;

    public OwnerServiceImpl(@Value("${backend.server.url}") String backendServerUrl, CacheManager cacheManager) {
        this.backendServerUrl = backendServerUrl;
        this.cacheManager = cacheManager;
    }

    @Override
    public Content<OwnerViewModel> getAll(int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "?page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public Optional<OwnerViewModel> getOwnerById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id, Optional.class);
    }

    @Override
    public OwnerViewModel getOwnerByUserId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/users/" + id, OwnerViewModel.class);
    }

    @Override
    public OwnerViewModel saveOwner(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, owner, OwnerViewModel.class).getBody();
    }

    @Override
    public void saveEditedOwner(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, owner);
    }

    @Override
    public void deleteOwner(Long id) {
        Optional<OwnerViewModel> owner = getOwnerById(id);
        Cache usersCache = cacheManager.getCache("usersCache");
        if (usersCache != null && owner.isPresent()) {
            usersCache.evict(owner.get().getUser().getLogin());
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id);
    }

    @Override
    public void updateOwnerDetails(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/details", owner);
    }
}
