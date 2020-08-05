package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.model.Rs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    MockMvc mockMvc;

    private List<Rs> list;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
        list = new ArrayList<Rs>();
        list.add(new Rs("第一条事件", "猪肉"));
        list.add(new Rs("第二条事件", "牛肉"));
        list.add(new Rs("第三条事件", "羊肉"));
    }

    @Test
    void contextLoads() {
        try {

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

    @Test
    public void given_one_new_Rs_and_index_then_modify () {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Rs rs1 = new Rs("ttt", "zzz");
            String rs1Json = objectMapper.writeValueAsString(rs1);
            String url1 = "/rs/modifyRs/1";
            mockMvc.perform(MockMvcRequestBuilders.post(url1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(rs1Json)).andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/1"))
                    .andExpect(jsonPath("$.name", is("ttt")))
                    .andExpect(jsonPath("$.keyword", is("zzz")))
                    .andExpect(status().isOk());

            Rs rs2 = new Rs("hasName", null);
            String rs2Json = objectMapper.writeValueAsString(rs2);
            String url2 = "/rs/modifyRs/2";
            mockMvc.perform(MockMvcRequestBuilders.post(url2)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(rs2Json)).andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/2"))
                    .andExpect(jsonPath("$.name", is("hasName")))
                    .andExpect(jsonPath("$.keyword", is("牛肉")))
                    .andExpect(status().isOk());

            Rs rs3 = new Rs(null, "hasKeyword");
            String rs3Json = objectMapper.writeValueAsString(rs3);
            String url3 = "/rs/modifyRs/3";
            mockMvc.perform(MockMvcRequestBuilders.post(url3)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(rs3Json)).andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/3"))
                    .andExpect(jsonPath("$.name", is("第三条事件")))
                    .andExpect(jsonPath("$.keyword", is("hasKeyword")))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_index_then_delete () {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/rs/deleteRs/2"))
                    .andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is("第一条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("猪肉")))
                    .andExpect(jsonPath("$[1].name", is("第三条事件")))
                    .andExpect(jsonPath("$[1].keyword", is("羊肉")))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
