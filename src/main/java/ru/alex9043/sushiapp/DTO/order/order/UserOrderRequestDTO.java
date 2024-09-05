package ru.alex9043.sushiapp.DTO.order.order;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@ToString
public class UserOrderRequestDTO {
    private Long addressId;
    private String deliveryType;
    private String paymentType;
    private String comment;
    private BigDecimal change;
    private Integer peopleCount;
    private Set<OrderItemRequestDTO> orderItems;
}
