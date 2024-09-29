package ru.alex9043.sushiapp.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewRequestDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewResponseDTO;
import ru.alex9043.sushiapp.service.product.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Schema(description = "Product")
@Validated
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "Get all reviews for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductReviewResponseDTO.class))),
    })
    @GetMapping("/{productId}/reviews")
    public List<ProductReviewResponseDTO> getReviews(@PathVariable Long productId) {
        return reviewService.getReviews(productId);
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
        return reviewService.createReview(productId, reviewRequestDTO);
    }
}
