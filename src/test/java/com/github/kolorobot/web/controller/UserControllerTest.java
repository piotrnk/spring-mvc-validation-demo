package com.github.kolorobot.web.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import com.github.kolorobot.domain.User;
import com.github.kolorobot.domain.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserRepository repo;

	@InjectMocks
	private UserController controller = new UserController();

	@Test
	public void list() throws Exception {
		List<User> users = Collections.singletonList(new User());
		when(repo.findAll()).thenReturn(users);

		MockMvcBuilders.standaloneSetup(controller).build()
				.perform(get("/user/list"))
				.andExpect(model().attribute("users", users))
				.andExpect(view().name("user/list"));

		verify(repo).findAll();
	}
}
