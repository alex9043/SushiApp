package ru.alex9043.sushiapp.DTO.product.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for tag id request")
@Data
public class TagIdResponseDTO {
    private Long id;
}
