package ru.alex9043.sushiapp.authorization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.alex9043.sushiapp.model.product.Role;
import ru.alex9043.sushiapp.model.user.District;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;
import ru.alex9043.sushiapp.repository.user.RefreshTokenRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Rollback
    @Test
    @Order(1)
    public void testSignUp() throws Exception {
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
        ResultActions resultActions = mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userNode.toString()))
                .andExpect(status().isOk());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode jsonResponse = objectMapper.readTree(response);
        assertThat(jsonResponse.has("accessToken")).isTrue();
        assertThat(jsonResponse.has("refreshToken")).isTrue();

        String accessToken = jsonResponse.get("accessToken").asText();
        String refreshToken = jsonResponse.get("refreshToken").asText();

        assertThat(accessToken).isNotBlank();
        assertThat(refreshToken).isNotBlank();

        assertThat(accessToken.split("\\.")).hasSize(3);
        assertThat(refreshToken.split("\\.")).hasSize(3);

        User user = userRepository.findByPhone(userNode.get("phone").asText()).orElseThrow();
        userRepository.delete(user);
        districtRepository.delete(savedDistrict);
    }

    @Transactional
    @Rollback
    @Test
    @Order(2)
    public void testSignIn() throws Exception {
        User user = new User();
        user.setName("Test");
        user.setPhone("+77777777777");
        user.setPassword(passwordEncoder.encode("Password"));
        user.setEmail("test@test.test");
        user.setRole(Role.ROLE_USER);
        user = userRepository.save(user);

        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("phone", "+77777777777");
        userNode.put("password", "Password");

        ResultActions resultActions = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userNode.toString()))
                .andExpect(status().isOk());

        String response = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode jsonResponse = objectMapper.readTree(response);
        assertThat(jsonResponse.has("accessToken")).isTrue();
        assertThat(jsonResponse.has("refreshToken")).isTrue();

        String accessToken = jsonResponse.get("accessToken").asText();
        String refreshToken = jsonResponse.get("refreshToken").asText();

        assertThat(accessToken).isNotBlank();
        assertThat(refreshToken).isNotBlank();

        assertThat(accessToken.split("\\.")).hasSize(3);

        refreshTokenRepository.deleteByUser(user);
        userRepository.delete(user);
    }

    @Transactional
    @Rollback
    @Test
    @Order(3)
    public void testRefreshToken() throws Exception {
        User user = new User();
        user.setName("Test");
        user.setPhone("+777777777777");
        user.setPassword(passwordEncoder.encode("Password"));
        user.setEmail("test@test.test");
        user.setRole(Role.ROLE_USER);
        user = userRepository.save(user);

        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("phone", "+777777777777");
        userNode.put("password", "Password");

        ResultActions resultActions = mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userNode.toString()))
                .andExpect(status().isOk());

        String response = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode jsonResponse = objectMapper.readTree(response);
        String refreshToken = jsonResponse.get("refreshToken").asText();

        ObjectNode refreshNode = objectMapper.createObjectNode();
        refreshNode.put("refreshToken", refreshToken);

        ResultActions refreshResultActions = mockMvc.perform(post("/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshNode.toString()))
                .andExpect(status().isOk());

        String refreshResponse = refreshResultActions.andReturn().getResponse().getContentAsString();
        JsonNode refreshJsonResponse = objectMapper.readTree(refreshResponse);
        assertThat(refreshJsonResponse.has("accessToken")).isTrue();
        assertThat(refreshJsonResponse.has("refreshToken")).isTrue();

        String accessToken = refreshJsonResponse.get("accessToken").asText();
        refreshToken = refreshJsonResponse.get("refreshToken").asText();

        assertThat(accessToken).isNotBlank();
        assertThat(refreshToken).isNotBlank();

        assertThat(accessToken.split("\\.")).hasSize(3);
        assertThat(refreshToken.split("\\.")).hasSize(3);
        refreshTokenRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
