package ru.alex9043.sushiapp.DTO.product.ingredient;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class IngredientsResponseDTO {
    Set<IngredientResponseDTO> ingredients;
}
