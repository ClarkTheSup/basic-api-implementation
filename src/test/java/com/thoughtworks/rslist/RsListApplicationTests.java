package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.model.Rs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        try {
            List<Rs> list = new ArrayList<Rs>();
            list.add(new Rs("第一条事件", "猪肉"));
            list.add(new Rs("第二条事件", "牛肉"));
            list.add(new Rs("第三条事件", "羊肉"));
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].name", is("第一条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("猪肉")))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_index_then_return_corresponding_Rs () {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/1"))
                    .andExpect(jsonPath("$.name", is("第一条事件")))
                    .andExpect(jsonPath("$.keyword", is("猪肉")))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_start_and_end_then_return_Rs_between_them () {
        try {
            int start = 1;
            int end = 2;
            String url = "/rs/sublist?start=" + start + "&end=" + end;
            mockMvc.perform(MockMvcRequestBuilders.get(url))
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is("第一条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("猪肉")))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_one_Rs_then_add_into_list () {
        try {
            Rs rs = new Rs("新增的事件", "热干面");
            ObjectMapper objectMapper = new ObjectMapper();
            String rsJson = objectMapper.writeValueAsString(rs);
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson))
                    .andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andExpect(jsonPath("$", hasSize(4)))
                    .andExpect(jsonPath("$[3].name", is("新增的事件")))
                    .andExpect(jsonPath("$[3].keyword", is("热干面")))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
