package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex9043.sushiapp.DTO.user.JwtAuthenticationResponseDTO;
import ru.alex9043.sushiapp.DTO.user.SignInRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignUpRequestDTO;
import ru.alex9043.sushiapp.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Schema(description = "Authorization")
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDTO signUp(@RequestBody SignUpRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDTO signIn(@RequestBody SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }
}
