package ru.alex9043.sushiapp.DTO.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequestDTO {
    private String name;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private String password;
    private String confirmPassword;

    private String addressName;
    private Long districtId;
    private String street;
    private Integer houseNumber;
    private Integer building;
    private Integer entrance;
    private Integer floor;
    private Integer apartmentNumber;
}
