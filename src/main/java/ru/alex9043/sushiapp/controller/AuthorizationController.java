package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex9043.sushiapp.DTO.user.JwtAuthenticationResponseDTO;
import ru.alex9043.sushiapp.DTO.user.RefreshTokenRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignInRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignUpRequestDTO;
import ru.alex9043.sushiapp.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Schema(description = "Authorization")
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Sign up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign up successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDTO signUp(@RequestBody SignUpRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Sign in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials")
    })
    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDTO signIn(@RequestBody SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }

    @Operation(summary = "Refresh Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials")
    })
    @PostMapping("/refresh-token")
    public JwtAuthenticationResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshToken) {
        return authenticationService.refreshToken(refreshToken);
    }
}
