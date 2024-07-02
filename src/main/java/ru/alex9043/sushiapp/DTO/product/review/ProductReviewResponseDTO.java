package ru.alex9043.sushiapp.DTO.product.review;

import lombok.Data;

@Data
public class ProductReviewResponseDTO {
    private Long id;
    private String reviewerName;
    private Integer rating;
    private String reviewText;
}
