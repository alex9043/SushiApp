package ru.alex9043.sushiapp.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductsResponseDTO;
import ru.alex9043.sushiapp.model.product.*;
import ru.alex9043.sushiapp.repository.product.CategoryRepository;
import ru.alex9043.sushiapp.repository.product.IngredientRepository;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.product.TagRepository;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;

    // Products
    private ProductResponseDTO mapToProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);

        Set<ProductReview> reviews = product.getProductReviews();
        if (reviews != null && !reviews.isEmpty()) {
            productResponseDTO.setRating(
                    reviews.stream()
                            .mapToInt(ProductReview::getRating)
                            .average()
                            .orElse(0.0)
            );
        } else {
            productResponseDTO.setRating(0.0);
        }

        Set<IngredientResponseDTO> ingredientsResponseDTO = product.getIngredients().stream().map(
                i -> modelMapper.map(i, IngredientResponseDTO.class)
        ).collect(Collectors.toSet());
        productResponseDTO.setIngredients(ingredientsResponseDTO);

        if (product.getImage() != null) {
            productResponseDTO.setBase64image(Base64.getEncoder().encodeToString(product.getImage()));
        } else {
            productResponseDTO.setBase64image("");
        }

        return productResponseDTO;
    }

    private void saveProductFields(ProductRequestDTO productRequestDTO, Product product) {
        byte[] imageBytes = new byte[0];
        if (productRequestDTO.getBase64Image() != null) {
            imageBytes = Base64.getDecoder().decode(productRequestDTO.getBase64Image());
        }
        product.setImage(imageBytes);
        Set<Tag> tags = productRequestDTO.getTags().stream().map(
                t -> tagRepository.findById(t).orElseThrow(
                        () -> new IllegalArgumentException("Tag not found")
                )
        ).collect(Collectors.toSet());
        Set<Category> categories = productRequestDTO.getCategories().stream().map(
                c -> categoryRepository.findById(c).orElseThrow(
                        () -> new IllegalArgumentException("Category not found")
                )
        ).collect(Collectors.toSet());
        Set<Ingredient> ingredients = productRequestDTO.getIngredients().stream().map(
                i -> ingredientRepository.findById(i).orElseThrow(
                        () -> new IllegalArgumentException("Ingredient not found")
                )
        ).collect(Collectors.toSet());
        product.setTags(tags);
        product.setCategories(categories);
        product.setIngredients(ingredients);
        productRepository.save(product);
    }

    public ProductsResponseDTO getProducts() {
        log.info("Fetching all products");
        List<ProductResponseDTO> productResponseDTOList = productRepository.findAll().stream().map(
                this::mapToProductResponseDTO
        ).toList();
        log.debug("Found {} products", productResponseDTOList.size());

        return ProductsResponseDTO.builder()
                .products(productResponseDTOList)
                .build();
    }

    public ProductsResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Creating a new product with name: {}", productRequestDTO.getName());

        Product product = modelMapper.map(productRequestDTO, Product.class);
        saveProductFields(productRequestDTO, product);
        return getProducts();
    }

    public ProductsResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long productId) {
        log.info("Updating product with ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found")
        );
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        saveProductFields(productRequestDTO, product);
        return getProducts();
    }

    public ProductsResponseDTO deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return getProducts();
    }
}
