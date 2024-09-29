package ru.alex9043.sushiapp.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewRequestDTO;
import ru.alex9043.sushiapp.DTO.product.review.ProductReviewResponseDTO;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.model.product.ProductReview;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.product.ProductReviewRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductReviewResponseDTO> getReviews(Long productId) {
        log.info("fetching reviews for product ID: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found"));

        return product.getProductReviews().stream()
                .map(r -> modelMapper.map(r, ProductReviewResponseDTO.class))
                .toList();
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
}
