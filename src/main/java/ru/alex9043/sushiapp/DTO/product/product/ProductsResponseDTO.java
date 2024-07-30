package ru.alex9043.sushiapp.DTO.product.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for products response")
@Data
@Builder
public class ProductsResponseDTO {
    List<ProductResponseDTO> products;
}
