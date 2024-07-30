package ru.alex9043.sushiapp.DTO.product.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Schema(description = "DTO for product with ingredients response")
@Data
public class ProductWithIngredientsResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<IngredientResponseDTO> ingredients;
}
