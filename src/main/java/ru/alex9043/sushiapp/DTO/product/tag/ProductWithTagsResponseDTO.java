package ru.alex9043.sushiapp.DTO.product.tag;

import lombok.Data;

import java.util.Set;

@Data
public class ProductWithTagsResponseDTO {
    private Long id;
    private String name;
    private Integer price;
    private Set<TagResponseDTO> tags;
}
