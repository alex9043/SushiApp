package ru.alex9043.sushiapp.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;

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
}
