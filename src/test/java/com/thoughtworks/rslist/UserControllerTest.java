package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_user() throws Exception {
        User user = new User("clark", "男", 19, "lkn@163.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void given_incorrect_phone_number_then_400() throws Exception {
        User user = new User("clark", "男", 19, "lkn@163.com",
                "11111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given_incorrect_email_then_400() throws Exception {
        User user = new User("clark", "男", 19, "lkn.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest());
    }
}
