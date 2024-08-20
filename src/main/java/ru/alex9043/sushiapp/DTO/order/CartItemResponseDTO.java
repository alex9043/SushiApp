package ru.alex9043.sushiapp.DTO.order;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long id;
    private Integer count;
    private ProductInCartItemResponseDTO product;
}
