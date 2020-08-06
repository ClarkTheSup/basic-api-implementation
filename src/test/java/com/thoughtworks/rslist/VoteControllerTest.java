package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RsRepository rsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

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
    @Order(7)
    public void given_start_end_time_then_find_vote_between_them() throws Exception {
        Vote vote1 = new Vote(1, 1, "5");
        Vote vote2 = new Vote(2, 1, "6");
        Vote vote3 = new Vote(3, 1, "7");
        ObjectMapper objectMapper = new ObjectMapper();
        String voteJson1 = objectMapper.writeValueAsString(vote1);
        String voteJson2 = objectMapper.writeValueAsString(vote2);
        String voteJson3 = objectMapper.writeValueAsString(vote3);
        mockMvc.perform(post("/rs/vote/1").contentType(MediaType.APPLICATION_JSON)
                .content(voteJson1)).andExpect(status().isCreated());
        mockMvc.perform(post("/rs/vote/1").contentType(MediaType.APPLICATION_JSON)
                .content(voteJson2)).andExpect(status().isCreated());
        mockMvc.perform(post("/rs/vote/1").contentType(MediaType.APPLICATION_JSON)
                .content(voteJson3)).andExpect(status().isCreated());
        mockMvc.perform(get("/vote/listBetweenTime?startTime=0&endTime=100"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
    }
}
