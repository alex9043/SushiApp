package ru.alex9043.sushiapp.DTO.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Schema(description = "DTO for product review response")
@Data
public class ProductReviewResponseDTO {
    private Long id;
    private String reviewerName;
    private Integer rating;
    private String reviewText;
    private Instant createdDate;
}
