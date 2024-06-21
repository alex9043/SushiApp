package ru.alex9043.sushiapp.DTO.product;

import lombok.Data;

import java.util.Set;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<ProductReviewResponseDTO> reviews;
    private Double rating;
}
