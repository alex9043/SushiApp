package ru.alex9043.sushiapp.DTO.product.ingredient;

import lombok.Data;

import java.util.Set;

@Data
public class ProductWithIngredientsResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<IngredientResponseDTO> ingredients;
}
