package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.product.*;
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
                    @ApiResponse(responseCode = "200", description = "Review created successfuly"),
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
}
