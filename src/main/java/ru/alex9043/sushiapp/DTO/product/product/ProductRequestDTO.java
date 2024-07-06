package ru.alex9043.sushiapp.DTO.product.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @NotNull(message = "Price must be specified")
    @Positive(message = "Price must be positive")
    private Integer price;
    private String base64Image;
}
