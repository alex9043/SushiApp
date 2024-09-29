package ru.alex9043.sushiapp.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.tag.TagRequestDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagsResponseDTO;
import ru.alex9043.sushiapp.model.product.Tag;
import ru.alex9043.sushiapp.repository.product.TagRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagsResponseDTO getTags() {
        log.info("Fetching all tags");
        Set<TagResponseDTO> tagResponseDTOSet = tagRepository.findAll().stream().map(
                t -> modelMapper.map(t, TagResponseDTO.class)
        ).collect(Collectors.toSet());
        log.debug("Found {} tags", tagResponseDTOSet.size());

        return TagsResponseDTO.builder()
                .tags(tagResponseDTOSet)
                .build();
    }

    public TagsResponseDTO createTag(TagRequestDTO tagRequestDTO) {
        log.info("Creating a new tag");
        Tag tag = modelMapper.map(tagRequestDTO, Tag.class);
        tag.setId(null);
        tagRepository.save(tag);
        return getTags();
    }

    public TagsResponseDTO updateTag(TagRequestDTO tagRequestDTO, Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new IllegalArgumentException("Tag not found")
        );
        tag.setName(tagRequestDTO.getName());
        tagRepository.save(tag);
        return getTags();
    }

    public TagsResponseDTO deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
        return getTags();
    }
}
