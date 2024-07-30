package ru.alex9043.sushiapp.DTO.product.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Schema(description = "DTO for product with tags response")
@Data
public class ProductWithTagsResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<TagResponseDTO> tags;
}
