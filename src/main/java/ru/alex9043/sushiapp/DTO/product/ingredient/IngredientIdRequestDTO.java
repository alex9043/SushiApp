package ru.alex9043.sushiapp.DTO.product.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for ingredient id request")
@Data
public class IngredientIdRequestDTO {
    private Long id;
}
