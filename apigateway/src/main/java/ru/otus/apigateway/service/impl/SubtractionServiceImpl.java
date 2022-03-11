package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.service.api.SubtractionService;


@Service
public class SubtractionServiceImpl implements SubtractionService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public void editThreshold(Integer value) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + "/api/subtraction/threshold", value);
    }

    @Override
    public Integer getThreshold() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(backendServerUrl + "/api/subtraction/threshold", Integer.class).getBody();
    }
}
