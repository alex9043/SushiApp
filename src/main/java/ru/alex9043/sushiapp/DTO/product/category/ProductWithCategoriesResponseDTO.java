package ru.alex9043.sushiapp.DTO.product.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Schema(description = "DTO for product with categories response")
@Data
public class ProductWithCategoriesResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<CategoryResponseDTO> tags;
}
