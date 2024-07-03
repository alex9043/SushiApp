package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.product.category.CategoriesIdRequestDTO;
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
    @GetMapping
    public ProductsResponseDTO getProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @Operation(summary = "Crate a new review for a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Review created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @PostMapping("/{productId}/reviews")
    public ProductReviewResponseDTO createReview(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReviewRequestDTO reviewRequestDTO
    ) {
        return productService.createReview(productId, reviewRequestDTO);
    }

    @Operation(summary = "Get all reviews for a product")
    @GetMapping("/{productId}/reviews")
    public List<ProductReviewResponseDTO> getReviews(@PathVariable Long productId) {
        return productService.getReviews(productId);
    }

    @Operation(summary = "Crate a new ingredient")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ingredient created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/ingredients")
    public IngredientResponseDTO createIngredient(
            @Valid @RequestBody IngredientRequestDTO ingredientRequestDTO
    ) {
        return productService.createIngredient(ingredientRequestDTO);
    }


    @Operation(summary = "Add ingredients to a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ingredients added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @PostMapping("/{productId}/ingredients")
    public ProductResponseDTO addIngredientsToProduct(@PathVariable Long productId, @RequestBody IngredientsIdRequestDTO ingredients) {
        return productService.addIngredientsToProduct(productId, ingredients);
    }

    @Operation(summary = "Crate a new tag")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tag created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/tags")
    public TagResponseDTO createTag(
            @Valid @RequestBody TagRequestDTO tagRequestDTO
    ) {
        return productService.createTag(tagRequestDTO);
    }


    @Operation(summary = "Add tags to a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tags added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @PostMapping("/{productId}/tags")
    public ProductResponseDTO addTagsToProduct(@PathVariable Long productId, @RequestBody TagsIdRequestDTO tags) {
        return productService.addTagsToProduct(productId, tags);
    }

    @Operation(summary = "Crate a new category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Category created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/categories")
    public CategoryResponseDTO createCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return productService.createCategory(categoryRequestDTO);
    }


    @Operation(summary = "Add categories to a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Categories added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
            })
    @PostMapping("/{productId}/categories")
    public ProductResponseDTO addCategoriesToProduct(@PathVariable Long productId, @RequestBody CategoriesIdRequestDTO categories) {
        return productService.addCategoriesToProduct(productId, categories);
    }
}
