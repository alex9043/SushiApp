package ru.alex9043.sushiapp.DTO.user;

import lombok.Data;

import java.util.List;

@Data
public class DistrictsResponseDTO {
    private List<DistrictResponseDTO> districts;
}
