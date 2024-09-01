package ru.alex9043.sushiapp.DTO.order.order;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@ToString
public class GuestOrderRequestDTO {
    private String userName;
    private String userPhone;
    private String userEmail;
    private Long districtId;
    private String street;
    private Integer houseNumber;
    private Integer building;
    private Integer entrance;
    private Integer floor;
    private Integer apartmentNumber;
    private String deliveryType;
    private String paymentType;
    private String comment;
    private BigDecimal change;
    private Integer peopleCount;
    private Set<OrderItemRequestDTO> orderItems;
}
