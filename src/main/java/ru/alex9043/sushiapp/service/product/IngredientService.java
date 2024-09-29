package ru.alex9043.sushiapp.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientRequestDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientsResponseDTO;
import ru.alex9043.sushiapp.model.product.Ingredient;
import ru.alex9043.sushiapp.repository.product.IngredientRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;


    public IngredientsResponseDTO getIngredients() {
        log.info("Fetching all ingredients");
        Set<IngredientResponseDTO> ingredientsResponseDTOSet = ingredientRepository.findAll().stream().map(
                i -> modelMapper.map(i, IngredientResponseDTO.class)
        ).collect(Collectors.toSet());
        log.debug("Found {} ingredients", ingredientsResponseDTOSet.size());

        return IngredientsResponseDTO.builder()
                .ingredients(ingredientsResponseDTOSet)
                .build();
    }

    public IngredientsResponseDTO createIngredient(IngredientRequestDTO ingredientRequestDTO) {
        log.info("Creating a new ingredient");
        Ingredient ingredient = modelMapper.map(ingredientRequestDTO, Ingredient.class);
        ingredient.setId(null);
        ingredientRepository.save(ingredient);
        return getIngredients();
    }

    public IngredientsResponseDTO updateIngredient(IngredientRequestDTO ingredientRequestDTO, Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(
                () -> new IllegalArgumentException("Ingredient not found")
        );
        ingredient.setName(ingredientRequestDTO.getName());
        ingredientRepository.save(ingredient);
        return getIngredients();
    }

    public IngredientsResponseDTO deleteIngredient(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
        return getIngredients();
    }
}
