package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.user.address.AddressResponseDTO;
import ru.alex9043.sushiapp.DTO.user.address.AddressesResponseDTO;
import ru.alex9043.sushiapp.DTO.user.address.DistrictResponseDTO;
import ru.alex9043.sushiapp.model.user.User;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public AddressesResponseDTO getAddressesForUser(UserDetails userDetails) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Set<AddressResponseDTO> addressesDTO = currentUser.getAddresses().stream().map(
                a -> {
                    AddressResponseDTO mappedAddress = modelMapper.map(a, AddressResponseDTO.class);
                    mappedAddress.setDistrict(modelMapper.map(a.getDistrict(), DistrictResponseDTO.class));
                    return mappedAddress;
                }
        ).collect(Collectors.toSet());

        return AddressesResponseDTO.builder()
                .addresses(addressesDTO)
                .build();
    }
}
