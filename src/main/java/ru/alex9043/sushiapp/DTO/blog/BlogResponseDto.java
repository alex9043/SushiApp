package ru.alex9043.sushiapp.DTO.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for blog response")

@Data
public class BlogResponseDto {
    private List<PostResponseDTO> blog;
}
