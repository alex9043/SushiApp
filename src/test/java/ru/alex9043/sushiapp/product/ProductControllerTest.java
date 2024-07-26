package ru.alex9043.sushiapp.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import ru.alex9043.sushiapp.DTO.product.category.CategoryResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;
import ru.alex9043.sushiapp.model.product.Role;
import ru.alex9043.sushiapp.model.user.District;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;
import ru.alex9043.sushiapp.repository.user.RefreshTokenRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {
    Logger log = LoggerFactory.getLogger(ProductControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    private String accessToken;

    @BeforeEach
    public void setUp() throws Exception {
        District district = new District();
        district.setName("test");
        District savedDistrict = districtRepository.save(district);

        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("name", "Alex Test");
        userNode.put("phone", "+77777777777");
        userNode.put("email", "test@test.test");
        userNode.put("dateOfBirth", LocalDate.of(1990, 1, 1).toString());
        userNode.put("password", "Password");
        userNode.put("confirmPassword", "Password");

        userNode.put("addressName", "Address 1");
        userNode.put("districtId", savedDistrict.getId());
        userNode.put("street", "First street");
        userNode.put("houseNumber", 1);
        userNode.put("building", 1);
        userNode.put("entrance", 1);
        userNode.put("floor", 1);
        userNode.put("apartmentNumber", 1);
        log.debug("Sign up POST request is started");
        ResultActions resultActions = mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userNode.toString()))
                .andExpect(status().isOk());
        log.debug("Sign up POST request is completed");
        String response = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode jsonResponse = objectMapper.readTree(response);

        accessToken = jsonResponse.get("accessToken").asText();
        log.debug("Access token: {}", accessToken);
        User user = userRepository.findByPhone("+77777777777").orElseThrow();
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() throws Exception {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
        districtRepository.deleteAll();
    }

    @Transactional
    @Rollback
    @Test
    public void testCreateProduct() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    public void testCreateProductAndReview() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        ObjectNode reviewNode = objectMapper.createObjectNode();
        reviewNode.put("reviewerName", "test");
        reviewNode.put("reviewText", "test");
        reviewNode.put("rating", 5);

        MvcResult productResponse = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/reviews")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewerName").value("test"))
                .andExpect(jsonPath("$.reviewText").value("test"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Transactional
    @Rollback
    @Test
    public void testCreateIngredientAndPutInProduct() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        ObjectNode ingredientNode1 = objectMapper.createObjectNode();
        ingredientNode1.put("name", "test1");
        MvcResult ingredientResponse1 = mockMvc.perform(post("/products/ingredients")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientNode1.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode ingredientNode2 = objectMapper.createObjectNode();
        ingredientNode2.put("name", "test2");
        MvcResult ingredientResponse2 = mockMvc.perform(post("/products/ingredients")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientNode2.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode ingredientNode3 = objectMapper.createObjectNode();
        ingredientNode3.put("name", "test3");
        MvcResult ingredientResponse3 = mockMvc.perform(post("/products/ingredients")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientNode3.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long ingredientId1 = objectMapper.readValue(
                ingredientResponse1.getResponse().getContentAsString(), IngredientResponseDTO.class).getId();
        Long ingredientId2 = objectMapper.readValue(
                ingredientResponse2.getResponse().getContentAsString(), IngredientResponseDTO.class).getId();
        Long ingredientId3 = objectMapper.readValue(
                ingredientResponse3.getResponse().getContentAsString(), IngredientResponseDTO.class).getId();
        ArrayNode ingredients = objectMapper.createArrayNode();
        ingredients.add(ingredientId1);
        ingredients.add(ingredientId2);
        ingredients.add(ingredientId3);

        ObjectNode ingredientsRequest = objectMapper.createObjectNode();
        ingredientsRequest.set("ingredients", ingredients);

        MvcResult productResponse = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/ingredients")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientsRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.ingredients[*].name",
                        Matchers.containsInAnyOrder("test1", "test2", "test3")));
    }

    @Test
    public void testCreateTagAndPutInProduct() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        ObjectNode tagNode1 = objectMapper.createObjectNode();
        tagNode1.put("name", "test1");
        MvcResult tagResponse1 = mockMvc.perform(post("/products/tags")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagNode1.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode tagNode2 = objectMapper.createObjectNode();
        tagNode2.put("name", "test2");
        MvcResult tagResponse2 = mockMvc.perform(post("/products/tags")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagNode2.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode tagNode3 = objectMapper.createObjectNode();
        tagNode3.put("name", "test3");
        MvcResult tagResponse3 = mockMvc.perform(post("/products/tags")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagNode3.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long tagId1 = objectMapper.readValue(
                tagResponse1.getResponse().getContentAsString(), TagResponseDTO.class).getId();
        Long tagId2 = objectMapper.readValue(
                tagResponse2.getResponse().getContentAsString(), TagResponseDTO.class).getId();
        Long tagId3 = objectMapper.readValue(
                tagResponse3.getResponse().getContentAsString(), TagResponseDTO.class).getId();
        ArrayNode tags = objectMapper.createArrayNode();
        tags.add(tagId1);
        tags.add(tagId2);
        tags.add(tagId3);

        ObjectNode tagsRequest = objectMapper.createObjectNode();
        tagsRequest.set("tags", tags);

        MvcResult productResponse = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/tags")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagsRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.tags[*].name",
                        Matchers.containsInAnyOrder("test1", "test2", "test3")));
    }

    @Test
    public void testCreateCategoryAndPutInProduct() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        ObjectNode categoryNode1 = objectMapper.createObjectNode();
        categoryNode1.put("name", "test1");
        MvcResult categoryResponse1 = mockMvc.perform(post("/products/categories")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryNode1.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode categoryNode2 = objectMapper.createObjectNode();
        categoryNode2.put("name", "test2");
        MvcResult categoryResponse2 = mockMvc.perform(post("/products/categories")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryNode2.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode categoryNode3 = objectMapper.createObjectNode();
        categoryNode3.put("name", "test3");
        MvcResult categoryResponse3 = mockMvc.perform(post("/products/categories")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryNode3.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long categoryId1 = objectMapper.readValue(
                categoryResponse1.getResponse().getContentAsString(), CategoryResponseDTO.class).getId();
        Long categoryId2 = objectMapper.readValue(
                categoryResponse2.getResponse().getContentAsString(), CategoryResponseDTO.class).getId();
        Long categoryId3 = objectMapper.readValue(
                categoryResponse3.getResponse().getContentAsString(), CategoryResponseDTO.class).getId();
        ArrayNode categories = objectMapper.createArrayNode();
        categories.add(categoryId1);
        categories.add(categoryId2);
        categories.add(categoryId3);

        ObjectNode categoriesRequest = objectMapper.createObjectNode();
        categoriesRequest.set("categories", categories);

        MvcResult productResponse = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/categories")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoriesRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.categories[*].name",
                        Matchers.containsInAnyOrder("test1", "test2", "test3")));
    }
}
