package ru.alex9043.sushiapp.DTO.order;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CartResponseDTO {
    private Long id;
    private Set<CartItemResponseDTO> cartItems;
}
