package ru.alex9043.sushiapp.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientRequestDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientsResponseDTO;
import ru.alex9043.sushiapp.service.product.IngredientService;

@RestController
@RequestMapping("/products/ingredients")
@RequiredArgsConstructor
@Schema(description = "Product")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    @Operation(summary = "Get all ingredients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tags get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientsResponseDTO.class))),
    })
    @GetMapping
    public IngredientsResponseDTO getIngredients() {
        return ingredientService.getIngredients();
    }

    @Operation(summary = "Crate a new ingredient",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ingredient created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IngredientResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = IngredientRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public IngredientsResponseDTO createIngredient(
            @Valid @RequestBody IngredientRequestDTO ingredientRequestDTO
    ) {
        return ingredientService.createIngredient(ingredientRequestDTO);
    }

    @Operation(summary = "Update ingredient",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ingredient updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IngredientsResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Ingredient update request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = IngredientRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{ingredientId}")
    public IngredientsResponseDTO updateIngredient(
            @Valid @RequestBody IngredientRequestDTO ingredientRequestDTO,
            @PathVariable(name = "ingredientId") Long ingredientId
    ) {
        return ingredientService.updateIngredient(ingredientRequestDTO, ingredientId);
    }

    @Operation(summary = "Delete ingredient",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ingredient updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IngredientsResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ingredientId}")
    public IngredientsResponseDTO deleteIngredient(
            @PathVariable(name = "ingredientId") Long ingredientId
    ) {
        return ingredientService.deleteIngredient(ingredientId);
    }
}
