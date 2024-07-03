package ru.alex9043.sushiapp.DTO.product.tag;

import lombok.Data;

import java.util.List;

@Data
public class TagsIdRequestDTO {
    List<Long> tags;
}
