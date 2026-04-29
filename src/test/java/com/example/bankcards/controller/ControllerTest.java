package com.example.bankcards.controller;


import com.example.bankcards.security.JwtService;
import com.example.bankcards.security.UserDetailService;
import com.example.bankcards.service.UserAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UserAuthService userAuthService;
    @MockitoBean
    private JwtService service;
    @MockitoBean
    private UserDetailService userDetailService;


    public void performRequest(RequestBuilder request,
                               ResultMatcher status,
                               ResultMatcher result) throws Exception {
        mvc.perform(request)
                .andExpect(status)
                .andExpect(result);
    }

    @Test
    public void getHello() throws Exception {
        System.out.println("Test 1: get \"/auth/hello\"");

        String output = "hello";

        performRequest(get("/auth/hello"),
                status().isOk(),
                content().string(output));

        /*mvc.perform(get("/auth/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(output));*/

        System.out.println("Test is passed");

    }

    @Test
    public void postAuthSignUp() throws Exception {

        String params = mapper.writeValueAsString(
                Map.of(
                        "username", "dbfdb",
                        "password", "fgbfdbdfs"));

        mvc.perform(
                post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(params))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("status", "User created!"));
    }
}
