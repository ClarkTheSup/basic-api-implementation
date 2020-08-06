package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.domain.Rs;
import com.thoughtworks.rslist.domain.User;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        User user1 = new User("clark", "男", 19, "lkn@163.com",
                "11111111111", 10);
        User user2 = new User("jack", "女", 25, "jack@163.com",
                "11111111112", 10);
        User user3 = new User("amy", "男", 28, "amy@163.com",
                "11111111113", 10);
        list.add(new Rs("第一条事件", "猪肉", user1));
        list.add(new Rs("第二条事件", "牛肉", user2));
        list.add(new Rs("第三条事件", "羊肉", user3));
    }

    @Test
    void contextLoads() {
        try {

            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].name", is("第一条事件")))
                    .andExpect(jsonPath("$[0].keyword", is("猪肉")))
                    .andExpect(jsonPath("$[0]", not(hasKey("user"))))
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
    public void given_out_of_bound_start_and_end_then_handle_exception() throws Exception {
        int start = -1;
        int end = 9;
        String url = "/rs/sublist?start=" + start + "&end=" + end ;
        mockMvc.perform(get(url))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void given_out_of_bound_index_then_handle_exception() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void when_add_Rs_given_null_attributes_then_handle_exception () {
        try {
            String rsJson1 = "{\"keyword\":\"热干面\"," +
                    "\"user\": {\"userName\":\"jim\",\"age\": 19,\"gender\": \"男\"," +
                    "\"email\": \"jim@163.com\",\"phone\": \"21111111111\"," +
                    "\"voteNum\": \"10\"}}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson1))
                    .andExpect(jsonPath("$.error", is("invalid param")))
                    .andExpect(status().isBadRequest());

            String rsJson2 = "{\"name\":\"新增的事件\"," +
                    "\"user\": {\"userName\":\"jim\",\"age\": 19,\"gender\": \"男\"," +
                    "\"email\": \"jim@163.com\",\"phone\": \"21111111111\"," +
                    "\"voteNum\": \"10\"}}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson2))
                    .andExpect(jsonPath("$.error", is("invalid param")))
                    .andExpect(status().isBadRequest());

            String rsJson3 = "{\"name\":\"新增的事件\",\"keyword\":\"热干面\"}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson3))
                    .andExpect(jsonPath("$.error", is("invalid param")))
                    .andExpect(status().isBadRequest());

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

    @Test
    public void when_add_Rs_given_null_attributes_then_400 () {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = new User("jim", "男", 19, "jim@163.com",
                    "21111111111", 10);
            Rs rs1 = new Rs(null, "热干面", user);
            String rsJson1 = "{\"keyword\":\"热干面\"," +
                    "\"user\": {\"userName\":\"jim\",\"age\": 19,\"gender\": \"男\"," +
                    "\"email\": \"jim@163.com\",\"phone\": \"21111111111\"," +
                    "\"voteNum\": \"10\"}}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson1))
                    .andExpect(status().isBadRequest());

            Rs rs2 = new Rs("新热搜", null, user);
            String rsJson2 = "{\"name\":\"新增的事件\"," +
                    "\"user\": {\"userName\":\"jim\",\"age\": 19,\"gender\": \"男\"," +
                    "\"email\": \"jim@163.com\",\"phone\": \"21111111111\"," +
                    "\"voteNum\": \"10\"}}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson2))
                    .andExpect(status().isBadRequest());

            Rs rs3 = new Rs("新热搜", "热干面", null);
            String rsJson3 = "{\"name\":\"新增的事件\",\"keyword\":\"热干面\"}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson3))
                    .andExpect(status().isBadRequest());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_one_Rs_then_add_into_list () {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            User user = new User("jim", "男", 19, "jim@163.com",
                    "21111111111", 10);
            Rs rs = new Rs("新增的事件", "热干面", user);
            //String rsJson = objectMapper.writeValueAsString(rs);
            String rsJson = "{\"name\":\"新增的事件\",\"keyword\":\"热干面\"," +
                    "\"user\": {\"userName\":\"jim\",\"age\": 19,\"gender\": \"男\"," +
                    "\"email\": \"jim@163.com\",\"phone\": \"21111111111\"," +
                    "\"voteNum\": \"10\"}}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(rsJson))
                    .andExpect(header().string("index", "3"))
                    .andExpect(status().isCreated());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andDo(print())
                    .andExpect(jsonPath("$", hasSize(4)))
                    .andExpect(jsonPath("$[3].name", is("新增的事件")))
                    .andExpect(jsonPath("$[3].keyword", is("热干面")))
                    //.andExpect(jsonPath("$[3].user", is(user)))
                    .andExpect(jsonPath("$[3]", not(hasKey("user"))))
                    .andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Test
//    public void given_one_new_Rs_and_index_then_modify () {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            User user = new User("jim", "男", 19, "jim@163.com",
//                    "21111111111", 10);
//            Rs rs1 = new Rs("ttt", "zzz", user);
//            String rs1Json = objectMapper.writeValueAsString(rs1);
//            String url1 = "/rs/modifyRs/1";
//            mockMvc.perform(MockMvcRequestBuilders.post(url1)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(rs1Json)).andExpect(status().isOk());
//            mockMvc.perform(MockMvcRequestBuilders.get("/rs/1"))
//                    .andExpect(jsonPath("$.name", is("ttt")))
//                    .andExpect(jsonPath("$.keyword", is("zzz")))
//                    .andExpect(jsonPath("$.user", is(user)))
//                    .andExpect(status().isOk());
//
//            Rs rs2 = new Rs("hasName", null);
//            String rs2Json = objectMapper.writeValueAsString(rs2);
//            String url2 = "/rs/modifyRs/2";
//            mockMvc.perform(MockMvcRequestBuilders.post(url2)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(rs2Json)).andExpect(status().isOk());
//            mockMvc.perform(MockMvcRequestBuilders.get("/rs/2"))
//                    .andExpect(jsonPath("$.name", is("hasName")))
//                    .andExpect(jsonPath("$.keyword", is("牛肉")))
//                    .andExpect(status().isOk());
//
//            Rs rs3 = new Rs(null, "hasKeyword");
//            String rs3Json = objectMapper.writeValueAsString(rs3);
//            String url3 = "/rs/modifyRs/3";
//            mockMvc.perform(MockMvcRequestBuilders.post(url3)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(rs3Json)).andExpect(status().isOk());
//            mockMvc.perform(MockMvcRequestBuilders.get("/rs/3"))
//                    .andExpect(jsonPath("$.name", is("第三条事件")))
//                    .andExpect(jsonPath("$.keyword", is("hasKeyword")))
//                    .andExpect(status().isOk());
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

//    }

}
