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
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.order.order.GuestOrderRequestDTO;
import ru.alex9043.sushiapp.DTO.order.order.OrderResponseDTO;
import ru.alex9043.sushiapp.DTO.order.order.UserOrderRequestDTO;
import ru.alex9043.sushiapp.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Order created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create order request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = GuestOrderRequestDTO.class)))
    @PostMapping("/guest")
    public OrderResponseDTO createGuestOrder(@RequestBody GuestOrderRequestDTO guestOrderRequestDTO) {
        return orderService.createGuestOrder(guestOrderRequestDTO);
    }

    @Operation(summary = "Create order",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Order created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create order request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserOrderRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/user")
    public OrderResponseDTO createUserOrder(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody UserOrderRequestDTO userOrderRequestDTO) {
        return orderService.createUserOrder(userDetails, userOrderRequestDTO);
    }

    @Operation(summary = "Get orders",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersResponseDTO.class))),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public OrdersResponseDTO getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return orderService.getOrders(userDetails);
    }
}
