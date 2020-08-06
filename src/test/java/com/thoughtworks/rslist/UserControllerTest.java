package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.UserController;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void should_register_user() throws Exception {
        User user = new User("clark", "男", 19, "lkn@163.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        //String userJson = objectMapper.writeValueAsString(user);
        String userJson = "{\"userName\":\"clark\",\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\",\"phone\": \"11111111111\"," +
                "\"voteNum\": \"10\"}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(header().string("index", "0"))
                .andExpect(status().isCreated());
    }

    @Test
    public void given_incorrect_phone_number_then_400() throws Exception {
        User user = new User("clark", "男", 19, "lkn@163.com",
                "11111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        //String userJson = objectMapper.writeValueAsString(user);
        String userJson = "{\"userName\":\"clark\",\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\",\"phone\": \"11111\"," +
                "\"voteNum\": \"10\"}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given_incorrect_email_then_400() throws Exception {
        User user = new User("clark", "男", 19, "lkn.com",
                "11111111111", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        //String userJson = objectMapper.writeValueAsString(user);
        String userJson = "{\"userName\":\"clark\",\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn.com\",\"phone\": \"11111\"," +
                "\"voteNum\": \"10\"}";
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
        String userJson1 = "{\"userName\":\"clark\",\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\",\"phone\": \"11111111111\"," +
                "\"voteNum\": \"10\"}";
        String userJson2 = "{\"userName\":\"clark\",\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\",\"phone\": \"11111111111\"," +
                "\"voteNum\": \"10\"}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson1))
                .andExpect(header().string("index", "0"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson2))
                .andExpect(header().string("index", "null"))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("clark")))
                .andExpect(jsonPath("$[0].gender", is("男")))
                .andExpect(status().isOk());

    }

    @Test
    public void when_get_users_then_return_user_list() throws Exception{
        String userJson1 = "{\"userName\":\"clark\",\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\",\"phone\": \"11111111111\"," +
                "\"voteNum\": \"10\"}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson1))
                .andExpect(header().string("index", "0"))
                .andExpect(status().isCreated());
        ResultActions resultActions = mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        //resultActions.andDo(print());
        resultActions.andExpect(content().string("[{\"userName\":\"clark\",\"gender\":\"男\"," +
                "\"age\":19,\"email\":\"lkn@163.com\",\"phone\":\"11111111111\",\"voteNum\":10}]"));

    }

    @Test
    public void given_incorrect_params_and_register_user_then_handle_exception() throws Exception{
        String userJson1 = "{\"age\": 19,\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\",\"phone\": \"11111111111\"," +
                "\"voteNum\": \"10\"}";
        String userJson2 = "{\"gender\": \"男\"," +
                "\"email\": \"lkn@163.com\"}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson1))
                .andExpect(jsonPath("$.error", is("invalid user")))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson2))
                .andExpect(jsonPath("$.error", is("invalid user")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_add_user_to_database() {

    }
}
