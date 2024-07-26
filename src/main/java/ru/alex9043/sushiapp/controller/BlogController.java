package ru.alex9043.sushiapp.controller;

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

    @GetMapping
    public BlogResponseDto getBlog() {
        return blogService.getBlog();
    }
}
