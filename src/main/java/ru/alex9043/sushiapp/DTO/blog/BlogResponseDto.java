package ru.alex9043.sushiapp.DTO.blog;

import lombok.Data;

import java.util.List;

@Data
public class BlogResponseDto {
    private List<PostResponseDTO> blog;
}
