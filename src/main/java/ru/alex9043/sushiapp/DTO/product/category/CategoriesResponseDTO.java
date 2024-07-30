package ru.alex9043.sushiapp.DTO.product.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for categories response")
@Data
@Builder
public class CategoriesResponseDTO {
    private List<CategoryResponseDTO> categories;
}
