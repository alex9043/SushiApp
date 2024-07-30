package ru.alex9043.sushiapp.DTO.product.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for ingredients id request")
@Data
public class IngredientsIdRequestDTO {
    List<Long> ingredients;
}
