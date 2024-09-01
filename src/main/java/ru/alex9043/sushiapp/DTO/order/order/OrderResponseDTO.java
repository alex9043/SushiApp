package ru.alex9043.sushiapp.DTO.order.order;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OrderResponseDTO {
    private String userName;
    private String street;
    private Integer houseNumber;
    private Integer building;
    private Integer entrance;
    private Integer floor;
    private Integer apartmentNumber;
    private String deliveryType;
    private String paymentType;
    private Set<OrderItemResponseDTO> orderItems;
}
