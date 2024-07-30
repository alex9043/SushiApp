package ru.alex9043.sushiapp.DTO.product.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for tag response")
@Data
public class TagResponseDTO {
    private Long id;
    private String name;
}
