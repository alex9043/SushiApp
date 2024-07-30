package ru.alex9043.sushiapp.DTO.product.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for category id response")
@Data
public class CategoriesIdRequestDTO {
    List<Long> categories;
}
