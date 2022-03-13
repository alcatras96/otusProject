package ru.otus.apigateway.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.otus.apigateway.validationgroup.Exist;
import ru.otus.apigateway.validationgroup.New;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionViewModel {

    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Long id;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class, Exist.class}, min = 4, max = 20)
    @Pattern(groups = {New.class, Exist.class}, regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
    private String name;
    @NotNull(groups = {New.class, Exist.class})
    private String imageUrl;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class, Exist.class}, min = 6, max = 100)
    private String description;
    @Min(groups = {New.class, Exist.class}, value = 1)
    @Max(groups = {New.class, Exist.class}, value = 999)
    private Integer price;
    @NotNull(groups = {New.class, Exist.class})
    @Valid
    private Long ownerId;
    private CategoryViewModel category;
}
