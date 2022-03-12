package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.service.api.SubtractionService;


@Service
public class SubtractionServiceImpl implements SubtractionService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/subtraction";

    private final String backendServerUrl;

    public SubtractionServiceImpl(@Value("${backend.server.url}") String backendServerUrl) {
        this.backendServerUrl = backendServerUrl;
    }

    @Override
    public void editThreshold(Integer value) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/threshold", value);
    }

    @Override
    public Integer getThreshold() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/threshold", Integer.class).getBody();
    }
}
