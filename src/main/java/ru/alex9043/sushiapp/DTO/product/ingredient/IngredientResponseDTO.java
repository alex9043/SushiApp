package ru.alex9043.sushiapp.DTO.product.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for ingredient response")
@Data
public class IngredientResponseDTO {
    private Long id;
    private String name;
}
