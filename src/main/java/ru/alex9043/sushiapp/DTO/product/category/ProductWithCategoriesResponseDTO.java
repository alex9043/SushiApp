package ru.alex9043.sushiapp.DTO.product.category;

import lombok.Data;

import java.util.Set;

@Data
public class ProductWithCategoriesResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<CategoryResponseDTO> tags;
}
