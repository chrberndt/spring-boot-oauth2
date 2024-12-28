package com.chberndt.spring.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OAuth2Controller.class)
@Import(OAuth2SecurityConfig.class)
class OAuth2ApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	void rootWhenAuthenticatedThen200() throws Exception {

		System.out.println("rootWhenAuthenticatedThen200()");

		// @formatter:off
		this.mvc
				.perform(get("/").with(jwt()))
				.andExpect(status().isOk());
		// @formatter:on
	}

	@Test
	void rootWhenUnauthenticatedThen401() throws Exception {

		System.out.println("rootWhenUnauthenticatedThen401()");

		// @formatter:off
		this.mvc.perform(get("/api/test"))
				.andExpect(status().isUnauthorized());
		// @formatter:on
	}

}
