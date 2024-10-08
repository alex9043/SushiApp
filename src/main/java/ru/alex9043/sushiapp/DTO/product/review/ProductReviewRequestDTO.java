package ru.alex9043.sushiapp.DTO.product.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Schema(description = "DTO for product review request")
@Data
public class ProductReviewRequestDTO {
    @Size(min = 2, max = 100, message = "Reviewer name must be between 2 and 100 characters")
    private String reviewerName;
    private String reviewText;
    @Range(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private Integer rating;
}
