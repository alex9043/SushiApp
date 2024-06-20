package ru.alex9043.sushiapp.DTO.product;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer price;
}
