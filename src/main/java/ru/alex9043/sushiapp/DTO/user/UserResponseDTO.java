package ru.alex9043.sushiapp.DTO.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
    private String name;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
}
