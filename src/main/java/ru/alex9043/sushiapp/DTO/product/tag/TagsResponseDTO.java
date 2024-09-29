package ru.alex9043.sushiapp.DTO.product.tag;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TagsResponseDTO {
    Set<TagResponseDTO> tags;
}
