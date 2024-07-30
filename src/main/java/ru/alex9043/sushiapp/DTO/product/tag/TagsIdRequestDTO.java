package ru.alex9043.sushiapp.DTO.product.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for tags id request")
@Data
public class TagsIdRequestDTO {
    List<Long> tags;
}
