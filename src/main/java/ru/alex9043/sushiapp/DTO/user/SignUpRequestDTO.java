package ru.alex9043.sushiapp.DTO.user;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequestDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phone;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email address")
    private String email;
    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotBlank(message = "Confirm password is mandatory")
    @Size(min = 8, message = "Confirm password must be at least 8 characters")
    private String confirmPassword;

    @NotBlank(message = "Address name is mandatory")
    @Size(max = 100, message = "Address name must be less than 100 characters")
    private String addressName;
    @NotNull(message = "District ID is mandatory")
    private Long districtId;
    @NotBlank(message = "Street is mandatory")
    @Size(max = 100, message = "Street must be less than 100 characters")
    private String street;
    @NotNull(message = "House number is mandatory")
    @Min(value = 1, message = "House number must be at least 1")
    private Integer houseNumber;
    private Integer building;
    private Integer entrance;
    private Integer floor;
    @NotNull(message = "Apartment number is mandatory")
    @Min(value = 1, message = "Apartment number must be at least 1")
    private Integer apartmentNumber;
}
