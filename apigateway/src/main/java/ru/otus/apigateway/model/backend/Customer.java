package ru.otus.apigateway.model.backend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private Long id;
    private String name;
    private String address;
    private Long userId;
    private Long billingAccountId;
    private Long statusId;
}
