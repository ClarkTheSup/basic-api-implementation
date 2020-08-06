package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void should_register_user_to_database() throws Exception {
        User user = new User("JIM", "男", 19, "lkn@163.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
        .content(userJson)).andExpect(status().isCreated());

        List<UserDto> all = userRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals("JIM", all.get(0).getUserName());
        Assertions.assertEquals(19, all.get(0).getAge());
    }

    @Test
    @Order(2)
    public void given_id_then_return_corresponding_user() throws Exception {
        User user1 = new User("JIM", "男", 19, "lkn@163.com",
                "11111111111", 10);
        User user2 = new User("Clark", "女", 19, "lkn@163.com",
                "11111111111", 10);
        User user3 = new User("Zip", "男", 19, "lkn@163.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson1 = objectMapper.writeValueAsString(user1);
        String userJson2 = objectMapper.writeValueAsString(user2);
        String userJson3 = objectMapper.writeValueAsString(user3);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(userJson1)).andExpect(status().isCreated());
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(userJson2)).andExpect(status().isCreated());
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(userJson3)).andExpect(status().isCreated());

        mockMvc.perform(get("/user/3"))
                .andExpect(jsonPath("$.userName", is("Zip")))
                .andExpect(jsonPath("$.gender", is("男")))
                .andExpect(status().isOk());
    }
    @Test
    @Order(3)
    public void given_id_then_delete_corresponding_user() throws Exception {
        User user1 = new User("JIM", "男", 19, "lkn@163.com",
                "11111111111", 10);
        User user2 = new User("Clark", "女", 19, "lkn@163.com",
                "11111111111", 10);
        User user3 = new User("Zip", "男", 19, "lkn@163.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson1 = objectMapper.writeValueAsString(user1);
        String userJson2 = objectMapper.writeValueAsString(user2);
        String userJson3 = objectMapper.writeValueAsString(user3);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(userJson1)).andExpect(status().isCreated());
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(userJson2)).andExpect(status().isCreated());
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(userJson3)).andExpect(status().isCreated());

        mockMvc.perform(get("/userDelete/3")).andExpect(status().isOk());
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userName", is("JIM")))
                .andExpect(jsonPath("$[1].userName", is("Clark")))
                .andExpect(status().isOk());
    }

}
