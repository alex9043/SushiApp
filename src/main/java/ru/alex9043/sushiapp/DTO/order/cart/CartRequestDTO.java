package ru.alex9043.sushiapp.DTO.order.cart;

import lombok.Data;

import java.util.Set;

@Data
public class CartRequestDTO {
    Set<CartItemRequestDTO> cart;
}
