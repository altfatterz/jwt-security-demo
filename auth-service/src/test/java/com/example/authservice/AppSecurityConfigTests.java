package com.example.authservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppSecurityConfigTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void unauthorizedAccess() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(header().exists("Location"))
        ;
    }

    @Test
    public void login() throws Exception {
        String content = "{\"username\":\"user\", \"password\":\"password\"}";
        mvc.perform(post("/login").content(content))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"));
    }

    @Test
    public void invalidLogin() throws Exception {
        String content = "{\"username\":\"user\", \"password\":\"wrong password\"}";
        mvc.perform(post("/login").content(content))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist("Authorization"));
    }
}
