package com.example;

import com.example.security.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityC22e1SecurityIntegrationTestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
//	@WithMockUser
	@WithUserDetails(userDetailsServiceBeanName = "customUserDetailService")
//	@WithCustomMockUser(priority = "LOW")
	void test1() throws Exception {
		mockMvc.perform(get("/demo"))
				.andExpect(status().isOk());
	}
}

/**
 * if it more than roles and authorities. use custom
 * 1.@WithMockUser - let the system create a user for you.
 * 2.@WithCustomMockUser(priority = "LOW") - create a custom user.
 * 3. @WithUserDetails(userDetailsServiceBeanName = "customUserDetailService") -
  -  you create the source of the user
 */
