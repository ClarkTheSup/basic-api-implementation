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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    public void given_duplicated_user_then_no_addition() throws Exception{
        User user1 = new User("clark", "男", 19, "lkn@163.com",
                "11111111111", 10);
        User user2 = new User("clark", "女", 25, "lkn@163.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson1 = objectMapper.writeValueAsString(user1);
        String userJson2 = objectMapper.writeValueAsString(user2);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson1))
                .andExpect(status().isOk());
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson2))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/list"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("clark")))
                .andExpect(jsonPath("$[0].gender", is("男")))
                .andExpect(status().isOk());

    }
}
