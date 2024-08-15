package ridhopriambodo.buana.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import ridhopriambodo.buana.entity.User;
import ridhopriambodo.buana.model.RegisterUserRequest;
import ridhopriambodo.buana.model.Response;
import ridhopriambodo.buana.model.UserResponse;
import ridhopriambodo.buana.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

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
    void getUserUnauthorized() throws Exception {

        mockMvc.perform(
                get("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-TOKEN-API","nothing")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            Response<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserSuccess() throws Exception {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("tes");
        user.setToken("tes");
        user.setTokenExpiredAt(System.currentTimeMillis()+1000000);
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        userRepository.save(user);


        mockMvc.perform(
                get("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-TOKEN-API","tes")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Response<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("test@gmail.com", response.getData().getEmail());
            assertEquals("tes", response.getData().getName());

        });
    }

    @Test
    void getUserExpired() throws Exception {
        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setName("tes1");
        user.setToken("tes1");
        user.setTokenExpiredAt(System.currentTimeMillis()-1000000);
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        userRepository.save(user);


        mockMvc.perform(
                get("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-TOKEN-API","tes1")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            Response<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());

        });
    }


}