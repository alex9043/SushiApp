package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.user.address.AddressRequestDTO;
import ru.alex9043.sushiapp.DTO.user.address.AddressResponseDTO;
import ru.alex9043.sushiapp.DTO.user.address.AddressesResponseDTO;
import ru.alex9043.sushiapp.DTO.user.address.DistrictResponseDTO;
import ru.alex9043.sushiapp.model.address.Address;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.AddressRepository;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final DistrictRepository districtRepository;
    private final AddressRepository addressRepository;

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

    public AddressesResponseDTO addAddress(UserDetails userDetails, AddressRequestDTO addressRequestDTO) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Address address = modelMapper.map(addressRequestDTO, Address.class);
        address.setId(null);
        address.setUser(currentUser);
        addressRepository.save(address);

        return getAddressesForUser(currentUser);
    }

    public AddressesResponseDTO changeAddress(UserDetails userDetails, String addressId, AddressRequestDTO addressRequestDTO) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Address address = addressRepository.findById(Long.valueOf(addressId)).orElseThrow(
                () -> new RuntimeException("Address not found")
        );
        address.setName(addressRequestDTO.getName());
        address.setStreet(addressRequestDTO.getStreet());
        address.setHouseNumber(addressRequestDTO.getHouseNumber());
        address.setBuilding(addressRequestDTO.getBuilding());
        address.setEntrance(addressRequestDTO.getEntrance());
        address.setFloor(addressRequestDTO.getFloor());
        address.setApartmentNumber(addressRequestDTO.getApartmentNumber());
        address.setDistrict(districtRepository.findById(addressRequestDTO.getDistrictId()).orElseThrow(
                () -> new RuntimeException("District not found")
        ));
        addressRepository.save(address);

        return getAddressesForUser(currentUser);
    }

    public AddressesResponseDTO removeAddress(UserDetails userDetails, String addressId) {
        log.debug("Address id - {}", addressId);
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Address address = addressRepository.findById(Long.valueOf(addressId)).orElseThrow(
                () -> new RuntimeException("Address not found")
        );
        addressRepository.delete(address);
        return getAddressesForUser(currentUser);
    }
}
