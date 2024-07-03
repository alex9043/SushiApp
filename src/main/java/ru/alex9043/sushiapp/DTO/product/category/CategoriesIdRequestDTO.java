package ru.alex9043.sushiapp.DTO.product.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesIdRequestDTO {
    List<Long> categories;
}
