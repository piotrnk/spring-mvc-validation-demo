package com.github.kolorobot.web.controller;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.server.setup.MockMvcBuilders;

public class IndexControllerTest {

	@Test
	public void index() throws Exception {
		MockMvcBuilders.standaloneSetup(new IndexController()).build()
				.perform(get("/"))
				.andExpect(view().name("index"))
				.andExpect(model().size(0));				
	}

}
