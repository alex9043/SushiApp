package ru.alex9043.sushiapp.DTO.blog;

import lombok.Data;

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
