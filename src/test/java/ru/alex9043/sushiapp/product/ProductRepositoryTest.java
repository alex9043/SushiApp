package ru.alex9043.sushiapp.product;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.alex9043.sushiapp.model.product.*;
import ru.alex9043.sushiapp.repository.product.*;

import java.math.BigDecimal;
import java.util.Objects;

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
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("Test");
        product.setPrice(BigDecimal.valueOf(300.0));

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test", savedProduct.getName());
        assertEquals(BigDecimal.valueOf(300.0), savedProduct.getPrice());
    }

    @Test
    public void testSaveReview() {
        Product product = new Product();
        product.setName("Test");
        product.setPrice(BigDecimal.valueOf(300.0));

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

    @Test
    public void testSaveIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Test1");
        Ingredient savedIngredient1 = ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Test2");
        Ingredient savedIngredient2 = ingredientRepository.save(ingredient2);

        Product product = new Product();
        product.setName("Test");
        product.setPrice(BigDecimal.valueOf(300.0));
        Product savedProduct = productRepository.save(product);

        savedProduct.getIngredients().add(savedIngredient1);
        savedProduct.getIngredients().add(savedIngredient2);
        savedProduct = productRepository.save(savedProduct);

        assertNotNull(ingredient1.getId());
        assertNotNull(ingredient2.getId());
        assertEquals("Test", savedProduct.getName());
        assertEquals("Test1", savedProduct.getIngredients().stream().filter(
                i -> Objects.equals(i.getId(), savedIngredient1.getId())
        ).findFirst().get().getName());
        assertEquals("Test2", savedProduct.getIngredients().stream().filter(
                i -> Objects.equals(i.getId(), savedIngredient2.getId())
        ).findFirst().get().getName());
    }

    @Test
    public void testSaveTags() {
        Tag tag1 = new Tag();
        tag1.setName("Test1");
        Tag savedTag1 = tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("Test2");
        Tag savedTag2 = tagRepository.save(tag2);

        Product product = new Product();
        product.setName("Test");
        product.setPrice(BigDecimal.valueOf(300.0));
        Product savedProduct = productRepository.save(product);

        savedProduct.getTags().add(savedTag1);
        savedProduct.getTags().add(savedTag2);
        savedProduct = productRepository.save(savedProduct);

        assertNotNull(tag1.getId());
        assertNotNull(tag2.getId());
        assertEquals("Test", savedProduct.getName());
        assertEquals("Test1", savedProduct.getTags().stream().filter(
                i -> Objects.equals(i.getId(), savedTag1.getId())
        ).findFirst().get().getName());
        assertEquals("Test2", savedProduct.getTags().stream().filter(
                i -> Objects.equals(i.getId(), savedTag2.getId())
        ).findFirst().get().getName());
    }

    @Test
    public void testSaveCategorys() {
        Category category1 = new Category();
        category1.setName("Test1");
        Category savedCategory1 = categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Test2");
        Category savedCategory2 = categoryRepository.save(category2);

        Product product = new Product();
        product.setName("Test");
        product.setPrice(BigDecimal.valueOf(300.0));
        Product savedProduct = productRepository.save(product);

        savedProduct.getCategories().add(savedCategory1);
        savedProduct.getCategories().add(savedCategory2);
        savedProduct = productRepository.save(savedProduct);

        assertNotNull(category1.getId());
        assertNotNull(category2.getId());
        assertEquals("Test", savedProduct.getName());
        assertEquals("Test1", savedProduct.getCategories().stream().filter(
                i -> Objects.equals(i.getId(), savedCategory1.getId())
        ).findFirst().get().getName());
        assertEquals("Test2", savedProduct.getCategories().stream().filter(
                i -> Objects.equals(i.getId(), savedCategory2.getId())
        ).findFirst().get().getName());
    }
}
