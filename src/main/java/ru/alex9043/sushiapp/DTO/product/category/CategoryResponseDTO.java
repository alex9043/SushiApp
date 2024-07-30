package ru.alex9043.sushiapp.DTO.product.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for category response")
@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
}
