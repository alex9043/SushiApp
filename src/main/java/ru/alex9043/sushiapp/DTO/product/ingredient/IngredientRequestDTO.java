package ru.alex9043.sushiapp.DTO.product.ingredient;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IngredientRequestDTO {
    @Size(min = 2, max = 100, message = "Ingredient name must be between 2 and 100 characters")
    private String name;
}
