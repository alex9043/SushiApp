package ru.alex9043.sushiapp.DTO.products;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductsResponseDTO {
    List<ProductResponseDTO> products;
}
