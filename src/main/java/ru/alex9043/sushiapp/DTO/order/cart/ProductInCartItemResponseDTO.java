package ru.alex9043.sushiapp.DTO.order.cart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInCartItemResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String base64image;
}
