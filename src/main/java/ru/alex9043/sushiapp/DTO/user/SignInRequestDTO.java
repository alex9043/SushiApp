package ru.alex9043.sushiapp.DTO.user;

import lombok.Data;

@Data
public class SignInRequestDTO {
    private String phone;
    private String password;
}
