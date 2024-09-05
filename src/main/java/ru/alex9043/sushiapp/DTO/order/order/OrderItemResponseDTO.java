package ru.alex9043.sushiapp.DTO.order.order;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Integer count;
    private ProductInOrderItemResponseDTO product;
}
