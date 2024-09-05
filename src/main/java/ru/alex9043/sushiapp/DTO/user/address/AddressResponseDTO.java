package ru.alex9043.sushiapp.DTO.user.address;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long id;
    private String name;
    private String street;
    private Integer houseNumber;
    private Integer building;
    private Integer entrance;
    private Integer floor;
    private Integer apartmentNumber;
    private DistrictResponseDTO district;
}
