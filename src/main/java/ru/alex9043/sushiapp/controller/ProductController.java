package ru.alex9043.sushiapp.controller;

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
import ru.alex9043.sushiapp.DTO.product.category.CategoriesIdRequestDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoriesResponseDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryRequestDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientRequestDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientsIdRequestDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductsResponseDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewRequestDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagRequestDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagsIdRequestDTO;
import ru.alex9043.sushiapp.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Schema(description = "Product")
@Validated
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductsResponseDTO.class))),
    })
    @GetMapping
    public ProductsResponseDTO getProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "Create a new product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @Operation(summary = "Crate a new review for a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Review created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductReviewResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductReviewRequestDTO.class)))
    @PostMapping("/{productId}/reviews")
    public ProductReviewResponseDTO createReview(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReviewRequestDTO reviewRequestDTO
    ) {
        return productService.createReview(productId, reviewRequestDTO);
    }

    @Operation(summary = "Get all reviews for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductReviewResponseDTO.class))),
    })
    @GetMapping("/{productId}/reviews")
    public List<ProductReviewResponseDTO> getReviews(@PathVariable Long productId) {
        return productService.getReviews(productId);
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
    @PostMapping("/ingredients")
    public IngredientResponseDTO createIngredient(
            @Valid @RequestBody IngredientRequestDTO ingredientRequestDTO
    ) {
        return productService.createIngredient(ingredientRequestDTO);
    }


    @Operation(summary = "Add ingredients to a product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ingredients added successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = IngredientsIdRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{productId}/ingredients")
    public ProductResponseDTO addIngredientsToProduct(
            @PathVariable Long productId,
            @RequestBody IngredientsIdRequestDTO ingredients) {
        return productService.addIngredientsToProduct(productId, ingredients);
    }

    @Operation(summary = "Crate a new tag",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tag created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TagRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/tags")
    public TagResponseDTO createTag(
            @Valid @RequestBody TagRequestDTO tagRequestDTO
    ) {
        return productService.createTag(tagRequestDTO);
    }


    @Operation(summary = "Add tags to a product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tags added successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TagsIdRequestDTO.class)))
    @PostMapping("/{productId}/tags")
    public ProductResponseDTO addTagsToProduct(
            @PathVariable Long productId,
            @RequestBody TagsIdRequestDTO tags) {
        return productService.addTagsToProduct(productId, tags);
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
    @PostMapping("/categories")
    public CategoryResponseDTO createCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return productService.createCategory(categoryRequestDTO);
    }


    @Operation(summary = "Add categories to a product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Categories added successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CategoriesIdRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{productId}/categories")
    public ProductResponseDTO addCategoriesToProduct(
            @PathVariable Long productId,
            @RequestBody CategoriesIdRequestDTO categories) {
        return productService.addCategoriesToProduct(productId, categories);
    }

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriesResponseDTO.class))),
    })
    @GetMapping("/categories")
    public CategoriesResponseDTO getCategories() {
        return productService.getCategories();
    }
}
