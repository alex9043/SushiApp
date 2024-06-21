package ru.alex9043.sushiapp.DTO.product;

import lombok.Data;

@Data
public class ProductReviewResponseDTO {
    private Long id;
    private String reviewerName;
    private String reviewText;
    private Integer rating;
}
