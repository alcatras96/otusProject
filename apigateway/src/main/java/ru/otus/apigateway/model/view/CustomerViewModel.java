package ru.otus.apigateway.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.otus.apigateway.transfer.Exist;
import ru.otus.apigateway.transfer.New;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerViewModel {
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Long id;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class, Exist.class}, min = 6, max = 20)
    @Pattern(groups = {New.class, Exist.class}, regexp="^[A-z]+$")
    private String name;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class, Exist.class}, min = 6, max = 25)
    @Pattern(groups = {New.class, Exist.class}, regexp="^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
    private String address;
    @NotNull(groups = {New.class, Exist.class})
    @Valid
    private UserViewModel user;
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    @Valid
    private BillingAccountViewModel billingAccount;
    @NotNull(groups = {Exist.class})
    private StatusViewModel status;
}
