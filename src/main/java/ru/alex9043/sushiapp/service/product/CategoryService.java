package ru.alex9043.sushiapp.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.category.CategoriesResponseDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryRequestDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;
import ru.alex9043.sushiapp.model.product.Category;
import ru.alex9043.sushiapp.repository.product.CategoryRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoriesResponseDTO getCategories() {
        log.info("Fetching all categories");
        Set<CategoryResponseDTO> categoryResponseDTOSet = categoryRepository.findAll().stream().map(
                c -> modelMapper.map(c, CategoryResponseDTO.class)
        ).collect(Collectors.toSet());
        log.debug("Found {} categories", categoryResponseDTOSet.size());
        categoryResponseDTOSet.forEach(
                c -> log.debug("Category id - {}", c.getId())
        );

        return CategoriesResponseDTO.builder()
                .categories(categoryResponseDTOSet)
                .build();
    }

    public CategoriesResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        log.info("Creating a new category");
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        category.setId(null);
        categoryRepository.save(category);
        return getCategories();
    }

    public CategoriesResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Category not found")
        );
        category.setName(categoryRequestDTO.getName());
        categoryRepository.save(category);
        return getCategories();
    }

    public CategoriesResponseDTO deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return getCategories();
    }
}
