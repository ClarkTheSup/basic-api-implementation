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

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;
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

    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

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
    public void given_start_end_time_then_find_vote_between_them() throws Exception {
        Date dNow = new Date();
        Vote vote1 = new Vote(1, 1, ft.format(dNow));
        sleep(1000);
        Vote vote2 = new Vote(2, 1, ft.format(dNow));
        sleep(1000);
        Vote vote3 = new Vote(3, 1, ft.format(dNow));

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

        String start_time = "1997-01-01 11:11:11";
        String end_time = "2997-01-01 11:11:11";
        mockMvc.perform(get("/vote/listBetweenTime?startTime="+ start_time +
                "&endTime=" + end_time))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
    }
}
