package ru.alex9043.sushiapp.DTO.user.address;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AddressesResponseDTO {
    private Set<AddressResponseDTO> addresses;
}
