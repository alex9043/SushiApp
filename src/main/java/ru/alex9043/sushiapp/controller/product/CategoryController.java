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
import ru.alex9043.sushiapp.DTO.product.category.CategoriesResponseDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryRequestDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;
import ru.alex9043.sushiapp.service.product.CategoryService;

@RestController
@RequestMapping("/products/categories")
@RequiredArgsConstructor
@Schema(description = "Product")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriesResponseDTO.class))),
    })
    @GetMapping
    public CategoriesResponseDTO getCategories() {
        return categoryService.getCategories();
    }

    @Operation(summary = "Crate a new category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Category created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CategoryRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public CategoriesResponseDTO createCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return categoryService.createCategory(categoryRequestDTO);
    }

    @Operation(summary = "Update category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriesResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CategoryRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{categoryId}")
    public CategoriesResponseDTO updateCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO,
            @PathVariable(name = "categoryId") Long categoryId
    ) {
        return categoryService.updateCategory(categoryRequestDTO, categoryId);
    }

    @Operation(summary = "Delete category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriesResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public CategoriesResponseDTO deleteCategory(
            @PathVariable(name = "categoryId") Long categoryId
    ) {
        return categoryService.deleteCategory(categoryId);
    }
}
