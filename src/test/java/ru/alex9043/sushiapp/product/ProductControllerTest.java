package ru.alex9043.sushiapp.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.alex9043.sushiapp.DTO.product.ingredient.IngredientResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk());
    }

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewNode.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewerName").value("test"))
                .andExpect(jsonPath("$.reviewText").value("test"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void testCreateIngredientAndPutInProduct() throws Exception {
        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("name", "test");
        productNode.put("price", 100);

        ObjectNode ingredientNode1 = objectMapper.createObjectNode();
        ingredientNode1.put("name", "test1");
        MvcResult ingredientResponse1 = mockMvc.perform(post("/products/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientNode1.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode ingredientNode2 = objectMapper.createObjectNode();
        ingredientNode2.put("name", "test2");
        MvcResult ingredientResponse2 = mockMvc.perform(post("/products/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientNode2.toString()))
                .andExpect(status().isOk())
                .andReturn();
        ObjectNode ingredientsRequest = objectMapper.createObjectNode();
        ObjectNode ingredientNode3 = objectMapper.createObjectNode();
        ingredientNode3.put("name", "test3");
        MvcResult ingredientResponse3 = mockMvc.perform(post("/products/ingredients")
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
        ingredientsRequest.set("ingredients", ingredients);

        MvcResult productResponse = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/ingredients")
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagNode1.toString()))
                .andExpect(status().isOk())
                .andReturn();

        ObjectNode tagNode2 = objectMapper.createObjectNode();
        tagNode2.put("name", "test2");
        MvcResult tagResponse2 = mockMvc.perform(post("/products/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagNode2.toString()))
                .andExpect(status().isOk())
                .andReturn();
        ObjectNode tagsRequest = objectMapper.createObjectNode();
        ObjectNode tagNode3 = objectMapper.createObjectNode();
        tagNode3.put("name", "test3");
        MvcResult tagResponse3 = mockMvc.perform(post("/products/tags")
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
        tagsRequest.set("tags", tags);

        MvcResult productResponse = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productNode.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Long productId = objectMapper.readValue(
                productResponse.getResponse().getContentAsString(), ProductResponseDTO.class).getId();

        mockMvc.perform(post("/products/" + productId + "/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagsRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.tags[*].name",
                        Matchers.containsInAnyOrder("test1", "test2", "test3")));
    }
}
