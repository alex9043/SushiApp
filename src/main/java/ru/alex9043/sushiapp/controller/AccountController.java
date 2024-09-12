package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex9043.sushiapp.DTO.user.UserResponseDTO;
import ru.alex9043.sushiapp.service.UserService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Schema(description = "Account")
public class AccountController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserResponseDTO getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserProfile(userDetails);
    }
}
