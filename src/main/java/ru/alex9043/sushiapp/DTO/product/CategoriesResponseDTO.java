package ru.alex9043.sushiapp.DTO.product;

import lombok.Builder;
import lombok.Data;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;

import java.util.List;

@Data
@Builder
public class CategoriesResponseDTO {
    private List<CategoryResponseDTO> categories;
}
