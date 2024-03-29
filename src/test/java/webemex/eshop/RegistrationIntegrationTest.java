package webemex.eshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import webemex.eshop.dto.AuthorizationDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@DisplayName("Test user registration, login, and profile retrieval")
@ActiveProfiles("local")
class RegistrationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUserRegistrationLoginAndProfile() throws Exception {
        // Step 1: Register a new user
        // Create an AuthorizationDTO with a unique username and other required information.
        AuthorizationDTO registrationRequest = new AuthorizationDTO();
        registrationRequest.setUsername("testUser_" + UUID.randomUUID());
        registrationRequest.setPassword("testPassword");
        registrationRequest.setName("Test");
        registrationRequest.setSurname("User");

        // Convert the registration request to a JSON string for the HTTP request.
        String registrationRequestJson = objectMapper.writeValueAsString(registrationRequest);

        // Send a POST request to register the new user.
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationRequestJson))
                .andExpect(status().isOk()); // Expect HTTP 200 OK

        // Step 2: Log in with the newly registered user
        // Create a login request with the username and password.
        AuthorizationDTO loginRequest = new AuthorizationDTO();
        loginRequest.setUsername(registrationRequest.getUsername());
        loginRequest.setPassword(registrationRequest.getPassword());

        // Convert the login request to a JSON string for the HTTP request.
        String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

        // Send a POST request to log in.
        var loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andReturn();

        // Extract the content from the login response.
        var loginResponseContent = loginResponse.getResponse().getContentAsString();
        AuthorizationDTO loginResponseDTO = objectMapper.readValue(loginResponseContent, AuthorizationDTO.class);

        // Get the token from the login response.
        String token = loginResponseDTO.getToken();
        assertNotNull(token, "Token should not be null"); // Ensure the token is not null

        // Step 3: Get the profile of the logged-in user
        // Set the authorization header with the token.
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Send a GET request to fetch the profile.
        var profileResponse = mockMvc.perform(get("/user/get-profile")
                        .headers(headers))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andReturn();

        // Extract the profile content from the response.
        var profileContent = profileResponse.getResponse().getContentAsString();

        // Deserialize the profile content into an AuthorizationDTO.
        AuthorizationDTO profileDTO = objectMapper.readValue(profileContent, AuthorizationDTO.class);

        // Validate that the profile information matches the registration request.
        assertEquals(registrationRequest.getName(), profileDTO.getAppUser().getName(), "Name mismatch");
        assertEquals(registrationRequest.getSurname(), profileDTO.getAppUser().getSurname(), "Surname mismatch");
        assertEquals(registrationRequest.getUsername(), profileDTO.getAppUser().getUsername(), "Username mismatch");
    }
}