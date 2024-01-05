package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityC21e2SecurityIntegrationTestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test1() throws Exception {
		mockMvc.perform(get("/demo")
						.with(jwt())
//					.with(opaqueToken())
				)
				.andExpect(status().isOk());
	}

	@Test
	//csrf doesn't make sense with oauth2 authorization and authentication service
	void test2() throws Exception {
		mockMvc.perform(post("/hello")
								.with(jwt())
//					.with(opaqueToken())
				)
				.andExpect(status().isOk());
	}
}

