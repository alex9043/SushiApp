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
import ru.alex9043.sushiapp.DTO.user.address.AddressIdRequestDTO;
import ru.alex9043.sushiapp.DTO.user.address.AddressRequestDTO;
import ru.alex9043.sushiapp.DTO.user.address.AddressesResponseDTO;
import ru.alex9043.sushiapp.DTO.user.address.DistrictsResponseDTO;
import ru.alex9043.sushiapp.service.AddressService;
import ru.alex9043.sushiapp.service.DistrictService;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Schema(description = "Address")
public class AddressController {
    private final DistrictService districtService;
    private final AddressService addressService;

    @Operation(summary = "Get districts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all districts",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DistrictsResponseDTO.class))),
    })
    @GetMapping("/districts")
    public DistrictsResponseDTO getDistricts() {
        return districtService.getDistricts();
    }

    @Operation(summary = "Get addresses", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all addresses",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesResponseDTO.class))),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public AddressesResponseDTO getAddressesForUser(@AuthenticationPrincipal UserDetails userDetails) {
        return addressService.getAddressesForUser(userDetails);
    }

    @Operation(summary = "add address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "address added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Address add request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AddressRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public AddressesResponseDTO addAddress(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody AddressRequestDTO addressRequestDTO) {
        return addressService.addAddress(userDetails, addressRequestDTO);
    }

    @Operation(summary = "change address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "address changed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Address change request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AddressRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public AddressesResponseDTO changeAddress(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody AddressRequestDTO addressRequestDTO) {
        return addressService.changeAddress(userDetails, addressRequestDTO);
    }

    @Operation(summary = "delete address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "address delete successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Address delete request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AddressIdRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    public AddressesResponseDTO removeAddress(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody AddressIdRequestDTO addressIdRequestDTO) {
        return addressService.removeAddress(userDetails, addressIdRequestDTO);
    }
}
