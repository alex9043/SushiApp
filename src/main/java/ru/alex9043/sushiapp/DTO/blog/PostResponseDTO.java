package ru.alex9043.sushiapp.DTO.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO for post response")

@Data
public class PostResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String creationDate;
    private byte[] image;
    private String slug;
}
