package ru.alex9043.sushiapp.DTO.product.review;

import lombok.Data;

import java.time.Instant;

@Data
public class ProductReviewResponseDTO {
    private Long id;
    private String reviewerName;
    private Integer rating;
    private String reviewText;
    private Instant createdDate;
}
