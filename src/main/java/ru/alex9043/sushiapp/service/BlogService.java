package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.blog.BlogResponseDto;
import ru.alex9043.sushiapp.DTO.blog.PostResponseDTO;
import ru.alex9043.sushiapp.repository.blog.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public BlogResponseDto getBlog() {
        List<PostResponseDTO> blogList = postRepository.findAll().stream().map(
                post -> modelMapper.map(post, PostResponseDTO.class)
        ).toList();

        return modelMapper.map(blogList, BlogResponseDto.class);
    }
}
