package ridhopriambodo.buana.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import ridhopriambodo.buana.entity.User;
import ridhopriambodo.buana.model.*;
import ridhopriambodo.buana.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){

        userRepository.deleteAll();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("ridho@gmail.com");
        request.setName("ridho");
        request.setPassword("password");

        mockMvc.perform(
                post("/api/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("OK", response.getData());
        });
    }

    @Test
    void testRegisterFailed() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("");
        request.setName("");
        request.setPassword("");

        mockMvc.perform(
                post("/api/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference <>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void loginFailed() throws Exception {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("password");

        mockMvc.perform(
                post("/api/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()

        ).andDo(result -> {
                    Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

//            assertEquals("OK", response.getData());
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void loginFailedWrongPassword() throws Exception {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("test@gmail.com");
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        userRepository.save(user);


        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("password");

        mockMvc.perform(
                post("/api/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()

        ).andDo(result -> {
                    Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

//            assertEquals("OK", response.getData());
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void loginSuccess() throws Exception {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("nama");
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        userRepository.save(user);

        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()

        ).andDo(result -> {
                    Response<TokenResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());
                    assertNotNull(response.getData().getToken());
                    assertNotNull(response.getData().getExpiredAt());

                    User userDB = userRepository.findById("test@gmail.com").orElse(null);
                    assertNotNull(userDB);
                    assertEquals(userDB.getToken(), response.getData().getToken());
                    assertEquals(userDB.getTokenExpiredAt(), response.getData().getExpiredAt());

                }
        );
    }

    @Test
    void logoutFailed() throws Exception {

        mockMvc.perform(
                delete("/api/logout")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            Response<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void logoutSuccess() throws Exception {
        User user = new User();
        user.setEmail("test11@gmail.com");
        user.setName("tes11");
        user.setToken("tes11");
        user.setTokenExpiredAt(System.currentTimeMillis()+1000000);
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        userRepository.save(user);

        mockMvc.perform(
                delete("/api/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-TOKEN-API","tes11")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

}