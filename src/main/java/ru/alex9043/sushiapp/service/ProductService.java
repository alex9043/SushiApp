package ru.alex9043.sushiapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.category.CategoriesIdRequestDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoriesResponseDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryRequestDTO;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientRequestDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientsIdRequestDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductsResponseDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewRequestDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagRequestDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagsIdRequestDTO;
import ru.alex9043.sushiapp.model.product.*;
import ru.alex9043.sushiapp.repository.product.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private final ProductReviewRepository productReviewRepository;
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;

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

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Creating a new product with name: {}", productRequestDTO.getName());
        byte[] imageBytes = new byte[0];
        if (productRequestDTO.getBase64Image() != null) {
            imageBytes = Base64.getDecoder().decode(productRequestDTO.getBase64Image());
        }
        Product product = modelMapper.map(productRequestDTO, Product.class);
        if (imageBytes.length != 0) {
            product.setImage(imageBytes);
        } else {
            product.setImage(new byte[0]);
        }
        log.debug("Created product with ID: {}", product.getId());
        Product createdProduct = productRepository.save(product);
        log.debug("Created product with ID: {}", createdProduct.getId());
        return mapToProductResponseDTO(createdProduct);
    }

    public ProductReviewResponseDTO createReview(Long productId, ProductReviewRequestDTO reviewRequestDTO) {
        log.info("Creating a new review for product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));
        ProductReview review = modelMapper.map(reviewRequestDTO, ProductReview.class);
        review.setProduct(product);
        review.setCreatedDate(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toInstant());

        review = productReviewRepository.save(review);
        log.debug("Created review with ID: {}", review.getId());
        return modelMapper.map(review, ProductReviewResponseDTO.class);
    }

    public List<ProductReviewResponseDTO> getReviews(Long productId) {
        log.info("fetching reviews for product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));

        return product.getProductReviews().stream()
                .map(r -> modelMapper.map(r, ProductReviewResponseDTO.class))
                .toList();
    }

    public IngredientResponseDTO createIngredient(IngredientRequestDTO ingredientRequestDTO) {
        log.info("Creating a new ingredient");
        Ingredient ingredient = modelMapper.map(ingredientRequestDTO, Ingredient.class);
        return modelMapper.map(ingredientRepository.save(ingredient), IngredientResponseDTO.class);
    }

    @Transactional
    public ProductResponseDTO addIngredientsToProduct(Long productId, IngredientsIdRequestDTO ingredientsId) {
        log.info("Adding ingredients to product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientsId.getIngredients());
        if (ingredients.size() != ingredientsId.getIngredients().size()) {
            throw new IllegalArgumentException("Some ingredients not found");
        }

        product.getIngredients().addAll(ingredients);
        productRepository.save(product);

        return mapToProductResponseDTO(product);
    }

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO) {
        log.info("Creating a new tag");
        Tag tag = modelMapper.map(tagRequestDTO, Tag.class);
        return modelMapper.map(tagRepository.save(tag), TagResponseDTO.class);
    }

    public ProductResponseDTO addTagsToProduct(Long productId, TagsIdRequestDTO tagsId) {
        log.info("Adding tags to product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));
        List<Tag> tags = tagRepository.findAllById(tagsId.getTags());
        if (tags.size() != tagsId.getTags().size()) {
            throw new IllegalArgumentException("Some tags not found");
        }

        product.getTags().addAll(tags);
        productRepository.save(product);

        return mapToProductResponseDTO(product);
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        log.info("Creating a new category");
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryResponseDTO.class);
    }

    public ProductResponseDTO addCategoriesToProduct(Long productId, CategoriesIdRequestDTO categoriesId) {
        log.info("Adding categories to product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));
        List<Category> categories = categoryRepository.findAllById(categoriesId.getCategories());
        if (categories.size() != categoriesId.getCategories().size()) {
            throw new IllegalArgumentException("Some categories not found");
        }

        product.getCategories().addAll(categories);
        productRepository.save(product);

        return mapToProductResponseDTO(product);
    }

    public CategoriesResponseDTO getCategories() {
        log.info("Fetching all categories");
        List<CategoryResponseDTO> categoryResponseDTOList = categoryRepository.findAll().stream().map(
                c -> modelMapper.map(c, CategoryResponseDTO.class)
        ).toList();
        log.debug("Found {} categories", categoryResponseDTOList.size());

        return CategoriesResponseDTO.builder()
                .categories(categoryResponseDTOList)
                .build();
    }
}
