package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.user.address.DistrictResponseDTO;
import ru.alex9043.sushiapp.DTO.user.address.DistrictsResponseDTO;
import ru.alex9043.sushiapp.model.address.District;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;


    public DistrictsResponseDTO getDistricts() {
        List<District> districts = districtRepository.findAll();
        List<DistrictResponseDTO> districtResponseDTOList = districts.stream().map(
                d -> modelMapper.map(d, DistrictResponseDTO.class)).toList();
        districtResponseDTOList.forEach(d -> log.debug("District - {}", d.getName()));
        DistrictsResponseDTO districtsResponseDTO = new DistrictsResponseDTO();
        districtsResponseDTO.setDistricts(districtResponseDTOList);
        return districtsResponseDTO;
    }
}
