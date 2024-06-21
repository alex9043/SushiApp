package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.*;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.model.product.ProductReview;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.product.ProductReviewRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductReviewRepository productReviewRepository;

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
        return productResponseDTO;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Creating a new product with name: {}", productRequestDTO.getName());
        Product product = productRepository.save(modelMapper.map(productRequestDTO, Product.class));
        log.debug("Created product with ID: {}", product.getId());
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public ProductReviewResponseDTO createReview(Long productId, ProductReviewRequestDTO reviewRequestDTO) {
        log.info("Creating a new review for product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));
        ProductReview review = modelMapper.map(reviewRequestDTO, ProductReview.class);
        review.setProduct(product);

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
}
