package ru.otus.apigateway.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.otus.apigateway.transfer.Exist;
import ru.otus.apigateway.transfer.New;

import javax.validation.constraints.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserViewModel {

    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private String id;
    @NotNull(groups = {New.class, Exist.class})
    @Size(groups = {New.class}, min = 6, max = 20)
    @Pattern(groups = {New.class}, regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")
    private String login;
    @Email(groups = {New.class,Exist.class})
    private String email;
    @NotNull(groups = {New.class, Exist.class})
    @Pattern(groups = {New.class}, regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")
    @Size(groups = {New.class}, min = 6, max = 20)
    private String password;
    @NotNull(groups = {New.class, Exist.class})
    private RoleViewModel role;
}
