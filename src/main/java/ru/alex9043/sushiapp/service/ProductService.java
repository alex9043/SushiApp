package ru.alex9043.sushiapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
import ru.alex9043.sushiapp.model.product.Ingredient;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.model.product.ProductReview;
import ru.alex9043.sushiapp.model.product.Tag;
import ru.alex9043.sushiapp.repository.product.IngredientRepository;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.product.ProductReviewRepository;
import ru.alex9043.sushiapp.repository.product.TagRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
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

        return productResponseDTO;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Creating a new product with name: {}", productRequestDTO.getName());
        Product product = modelMapper.map(productRequestDTO, Product.class);
        log.debug("Created product with ID: {}", product.getId());
        Product createdProduct = productRepository.save(product);
        log.debug("Created product with ID: {}", product.getId());
        return modelMapper.map(createdProduct, ProductResponseDTO.class);
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
}
