package ru.alex9043.sushiapp.DTO.product.product;

import lombok.Data;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;

import java.util.Set;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<ProductReviewResponseDTO> reviews;
    private Set<IngredientResponseDTO> ingredients;
    private Set<TagResponseDTO> tags;
    private Double rating;
}
