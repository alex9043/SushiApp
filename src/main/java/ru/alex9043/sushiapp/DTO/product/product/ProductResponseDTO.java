package ru.alex9043.sushiapp.DTO.product.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;

import java.util.Set;

@Schema(description = "DTO for product response")
@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private String base64image;
    private Set<ProductReviewResponseDTO> reviews;
    private Set<IngredientResponseDTO> ingredients;
    private Set<TagResponseDTO> tags;
    private Set<CategoryResponseDTO> categories;
    private Double rating;
}
