package ru.alex9043.sushiapp.DTO.order.cart;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long id;
    private Integer count;
    private ProductInCartItemResponseDTO product;
}
