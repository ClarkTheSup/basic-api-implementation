package com.thoughtworks.rslist;

import com.thoughtworks.rslist.model.Rs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]")).andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_index_then_return_corresponding_Rs () {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/1"))
                    .andExpect(content().string("第一条事件")).andExpect(status().isOk());
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
                    .andExpect(content().string("[第一条事件]")).andExpect(status().isOk());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void given_one_Rs_then_add_into_list () {
        try {
            String jsonStr = "{\"name\": \"新增的事件\"}";
            mockMvc.perform(MockMvcRequestBuilders.post("/rs/addRs")
                    .contentType(MediaType.APPLICATION_JSON).content(jsonStr))
                    .andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders.get("/rs/list"))
                    .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件, 新增的事件]"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
