package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.domain.Rs;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RsRepository rsRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
        rsRepository.deleteAll();
        UserDto userDto1 = UserDto.builder().userName("clark")
                .age(19).email("lkn@163.com").gender("男").phone("11111111111")
                .voteNum(10).build();
        UserDto userDto2 = UserDto.builder().userName("jim")
                .age(29).email("lkn@163.com").gender("女").phone("22111111111")
                .voteNum(10).build();
        UserDto userDto3 = UserDto.builder().userName("amy")
                .age(19).email("lkn@163.com").gender("男").phone("33111111111")
                .voteNum(10).build();
        userRepository.save(userDto1);
        userRepository.save(userDto2);
        userRepository.save(userDto3);
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
        rsRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void should_add_rs_to_database() throws Exception {
        String rsJson = "{\"name\": \"猪肉涨价了\"," +
                            "\"keyword\": \"猪\"," +
                            "\"userId\": \"1\"}";
        mockMvc.perform(post("/rs/addRs").contentType(MediaType.APPLICATION_JSON)
                .content(rsJson)).andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyword", is("猪")));
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void when_user_not_registered_then_return_400() throws Exception {
        String rsJson1 = "{\"name\": \"猪肉涨价了\"," +
                "\"keyword\": \"猪\"," +
                "\"userId\": \"1\"}";
        String rsJson2 = "{\"name\": \"猪肉涨价了\"," +
                "\"keyword\": \"猪\"," +
                "\"userId\": \"100\"}";
        mockMvc.perform(post("/rs/addRs").contentType(MediaType.APPLICATION_JSON)
                .content(rsJson1)).andExpect(status().isCreated());
        mockMvc.perform(post("/rs/addRs").contentType(MediaType.APPLICATION_JSON)
                .content(rsJson2)).andExpect(status().isBadRequest());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("猪肉涨价了")))
                .andExpect(jsonPath("$[0].keyword", is("猪")));
        mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void when_delete_user_then_delete_rs_cascadly() throws Exception {
        String rsJson1 = "{\"name\": \"猪肉涨价了\"," +
                "\"keyword\": \"猪\"," +
                "\"userId\": \"1\"}";
        String rsJson2 = "{\"name\": \"牛肉涨价了\"," +
                "\"keyword\": \"牛\"," +
                "\"userId\": \"2\"}";
        mockMvc.perform(post("/rs/addRs").contentType(MediaType.APPLICATION_JSON)
                .content(rsJson1)).andExpect(status().isCreated());
        mockMvc.perform(post("/rs/addRs").contentType(MediaType.APPLICATION_JSON)
                .content(rsJson2)).andExpect(status().isCreated());
        mockMvc.perform(get("/userDelete/2")).andExpect(status().isOk());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
