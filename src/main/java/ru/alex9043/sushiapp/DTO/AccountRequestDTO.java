package ru.alex9043.sushiapp.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountRequestDTO {
    String name;
    String phone;
    String email;
    LocalDate dateOfBirth;
}