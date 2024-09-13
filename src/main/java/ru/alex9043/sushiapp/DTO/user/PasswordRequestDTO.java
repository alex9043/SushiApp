package ru.alex9043.sushiapp.DTO.user;

import lombok.Data;

@Data
public class PasswordRequestDTO {
    String oldPassword;
    String password;
    String confirmPassword;
}