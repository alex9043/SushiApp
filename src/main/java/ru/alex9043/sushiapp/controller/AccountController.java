package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.AccountRequestDTO;
import ru.alex9043.sushiapp.DTO.user.*;
import ru.alex9043.sushiapp.service.AccountService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Schema(description = "Account")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Get all profiles", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get profiles",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersResponseDTO.class))),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public UsersResponseDTO getUsers() {
        return accountService.getUsers();
    }

    @Operation(summary = "Get user profile", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user profile",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdminUserResponseDTO.class))),
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public AdminUserResponseDTO getUser(@PathVariable(name = "userId") Long userId) {
        return accountService.getUser(userId);
    }

    @Operation(summary = "Change user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User changed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Profile change request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userId}")
    public UsersResponseDTO changeUser(@PathVariable(name = "userId") Long userId, @RequestBody UserRequestDTO user) {
        return accountService.changeUser(userId, user);
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public UsersResponseDTO deleteUser(@PathVariable(name = "userId") Long userId) {
        return accountService.deleteUser(userId);
    }

    @Operation(summary = "Get profile", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get profile",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public UserResponseDTO getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return accountService.getUserProfile(userDetails);
    }

    @Operation(summary = "Change profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile changed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Profile change request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AccountRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public UserResponseDTO changeUserProfile(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody AccountRequestDTO account) {
        return accountService.changeUserProfile(userDetails, account);
    }

    @Operation(summary = "Change password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "password changed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "password change request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PasswordRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/password")
    public UserResponseDTO changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody PasswordRequestDTO passwordRequestDTO) {
        return accountService.changePassword(userDetails, passwordRequestDTO);
    }

}
