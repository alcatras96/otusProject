package ru.otus.apigateway.model.backend;

import lombok.Builder;
import lombok.Data;
import ru.otus.apigateway.model.view.Status;

@Data
@Builder
public class Customer {

    private Long id;
    private String name;
    private String address;
    private Status status;
    private Long userId;
    private Long billingAccountId;
}
