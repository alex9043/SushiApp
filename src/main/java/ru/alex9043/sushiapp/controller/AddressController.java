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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
