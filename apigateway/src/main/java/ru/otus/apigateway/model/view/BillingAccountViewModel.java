package ru.otus.apigateway.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.otus.apigateway.validationgroup.Exist;
import ru.otus.apigateway.validationgroup.New;

import javax.validation.constraints.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingAccountViewModel {
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Long id;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class, Exist.class}, min = 16, max = 16)
    @Pattern(groups = {New.class, Exist.class}, regexp = "^[0-9]+$")
    private String number;
    @NotNull(groups = {New.class, Exist.class})
    @Min(groups = {New.class, Exist.class}, value = 100)
    @Max(groups = {New.class, Exist.class}, value = 999)
    private Integer cvv;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class, Exist.class}, min = 4, max = 20)
    private String validity;
    @Min(groups = {New.class, Exist.class}, value = 0)
    private Long balance;
}
