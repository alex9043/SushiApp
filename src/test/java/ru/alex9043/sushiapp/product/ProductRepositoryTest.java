package ru.alex9043.sushiapp.product;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.model.product.ProductReview;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.product.ProductReviewRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductReviewRepository reviewRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("Test");
        product.setPrice(300);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test", savedProduct.getName());
        assertEquals(300, savedProduct.getPrice());
    }

    @Test
    public void testSaveReview() {
        Product product = new Product();
        product.setName("Test");
        product.setPrice(300);

        Product savedProduct = productRepository.save(product);

        ProductReview review = new ProductReview();
        review.setReviewerName("Test");
        review.setReviewText("test");
        review.setRating(5);
        review.setProduct(savedProduct);

        ProductReview savedReview = reviewRepository.save(review);

        assertNotNull(savedReview.getId());
        assertEquals("Test", savedReview.getReviewerName());
        assertEquals("test", savedReview.getReviewText());
        assertEquals(5, savedReview.getRating());
        assertEquals(savedProduct, savedReview.getProduct());

    }
}
