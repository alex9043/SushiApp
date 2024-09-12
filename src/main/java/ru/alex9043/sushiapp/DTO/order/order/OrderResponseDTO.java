package ru.alex9043.sushiapp.DTO.order.order;

import lombok.Builder;
import lombok.Data;
import ru.alex9043.sushiapp.model.order.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String userName;
    private LocalDateTime createdDate;
    private OrderStatus orderStatus;
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
