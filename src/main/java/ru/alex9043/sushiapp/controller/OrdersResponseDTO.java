package ru.alex9043.sushiapp.controller;

import lombok.Data;
import ru.alex9043.sushiapp.DTO.order.order.OrderResponseDTO;

import java.util.Set;

@Data
public class OrdersResponseDTO {
    private Set<OrderResponseDTO> orders;
}
