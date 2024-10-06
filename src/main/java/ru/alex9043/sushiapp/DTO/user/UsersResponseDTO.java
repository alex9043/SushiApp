package ru.alex9043.sushiapp.DTO.user;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UsersResponseDTO {
    private Set<AdminUserResponseDTO> users;
}
