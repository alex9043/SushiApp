package ru.alex9043.sushiapp.DTO.user;

import lombok.Data;
import ru.alex9043.sushiapp.DTO.user.address.AddressResponseDTO;
import ru.alex9043.sushiapp.model.user.Role;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AdminUserResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private Set<Role> roles;
    private Set<AddressResponseDTO> addresses;
}
