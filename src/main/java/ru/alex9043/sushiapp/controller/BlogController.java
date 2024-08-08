package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex9043.sushiapp.DTO.blog.BlogResponseDto;
import ru.alex9043.sushiapp.service.BlogService;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @Operation(summary = "Get blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BlogResponseDto.class))),
    })
    @GetMapping
    public BlogResponseDto getBlog() {
        return blogService.getBlog();
    }
}
