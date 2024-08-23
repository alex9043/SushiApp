package ru.alex9043.sushiapp.DTO.order.cart;

import lombok.Data;

@Data
public class ProductInCartItemResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private String base64image;
}
