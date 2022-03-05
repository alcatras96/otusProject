package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.config.JwtTokenUtil;
import ru.otus.apigateway.model.view.AuthToken;
import ru.otus.apigateway.model.view.LoginUser;

import static ru.otus.apigateway.constants.Constants.TOKEN_PREFIX;

@AllArgsConstructor
@RestController
@RequestMapping("/api/token")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/generate-token")
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getLogin(),
                        loginUser.getPassword()
                )
        );
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @GetMapping(value = "/expDate")
    public ResponseEntity<?> GetExpDate(@RequestHeader(name = "authorization") String token) {
        token = token.replace(TOKEN_PREFIX, "");
        return ResponseEntity.ok(jwtTokenUtil.getExpirationDateFromToken(token));
    }

}
