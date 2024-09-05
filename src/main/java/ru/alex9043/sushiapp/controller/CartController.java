package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.order.cart.CartProductRequestDTO;
import ru.alex9043.sushiapp.DTO.order.cart.CartRequestDTO;
import ru.alex9043.sushiapp.DTO.order.cart.CartResponseDTO;
import ru.alex9043.sushiapp.service.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Get cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public CartResponseDTO getCartForUser(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getCartForUser(userDetails);
    }

    @Operation(summary = "Add product into cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product added successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CartResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product added into cart request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CartProductRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add")
    public CartResponseDTO addProductIntoCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartProductRequestDTO cartProductRequestDTO) {
        return cartService.addProductIntoCart(userDetails, cartProductRequestDTO);
    }

    @Operation(summary = "Remove product into cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product remove successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CartResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product removed into cart request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CartProductRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/remove")
    public CartResponseDTO removeProductIntoCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartProductRequestDTO cartProductRequestDTO) {
        return cartService.removeProductIntoCart(userDetails, cartProductRequestDTO);
    }

    @Operation(summary = "Refresh cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product refreshed successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CartResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product removed into cart request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CartRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/refresh")
    public CartResponseDTO refreshCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartRequestDTO cartRequestDTO) {
        return cartService.refreshCart(userDetails, cartRequestDTO);
    }

    @Operation(summary = "Remove cart",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cart remove successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeCart(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.removeCart(userDetails);
    }
}
