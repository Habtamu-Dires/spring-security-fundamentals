package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityC21e3SecurityIntegrationTestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser   // mock the authentication - it create a securityContext for the test only
	//the securityContext is customizable
	void test1() throws Exception {
		mockMvc.perform(get("/demo"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	void test2() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(authorities = "read")  //add authority read for the simulated user
	void test3() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk());
	}

}
