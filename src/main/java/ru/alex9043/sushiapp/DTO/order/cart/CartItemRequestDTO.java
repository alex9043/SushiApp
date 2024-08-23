package ru.alex9043.sushiapp.DTO.order.cart;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Integer count;
    private Long productId;
}
