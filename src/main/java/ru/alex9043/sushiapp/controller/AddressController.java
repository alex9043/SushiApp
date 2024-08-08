package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex9043.sushiapp.DTO.user.DistrictsResponseDTO;
import ru.alex9043.sushiapp.service.DistrictService;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Schema(description = "Address")
public class AddressController {
    private final DistrictService districtService;

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
}
