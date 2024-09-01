package ru.alex9043.sushiapp.DTO.order.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInOrderItemResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String base64image;
}
