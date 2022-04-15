package br.com.challenge.pix.itau.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;

import br.com.challenge.pix.itau.services.PixRegisterServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@WebMvcTest
public class PixRegisterControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PixRegisterServices services;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldHandleRequestCreatePixRegisterController() throws Exception {
        mockMvc.perform(post("/v1/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new PixRegisterRequest())))
                .andExpect(status().isOk());

    }

    @Test
    void shouldHandleRequestDeactivatePixRegisterController() throws Exception {
        mockMvc.perform(delete("/v1/api/register/{registerId}","132134124"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleRequestFindRegisterByIdController() throws Exception {
        mockMvc.perform(get("/v1/api/register/{registerId}","132134124"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleRequestFindPixRegistersFilteredController() throws Exception {
        mockMvc.perform(get("/v1/api/register/filter")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleRequestPatchPixRegisterController() throws Exception {
        mockMvc.perform(patch("/v1/api/register/{register_id}", "1231241")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new PixRegisterRequest())))
                .andExpect(status().isOk());

    }
}
