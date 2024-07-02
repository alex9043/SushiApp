package ru.alex9043.sushiapp.DTO.product.ingredient;

import lombok.Data;

import java.util.List;

@Data
public class IngredientsIdRequestDTO {
    List<Long> ingredients;
}
