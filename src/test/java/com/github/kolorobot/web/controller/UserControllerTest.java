package com.github.kolorobot.web.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.*;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.kolorobot.domain.User;
import com.github.kolorobot.domain.UserRepository;
import com.github.kolorobot.web.form.ProfileForm;

public class UserControllerTest {
	
	
	@Before
	public void before() {
		WebMvcConfig.initMocks();
	}
	
	
	@Test
	public void list() throws Exception {
		UserRepository userRepositoryMock = WebMvcConfig.userRepositoryMockInstance();
		List<User> users = Collections.singletonList(new User());

		when(userRepositoryMock.findAll()).thenReturn(users);
		
		annotationConfigSetup(WebMvcConfig.class).build()
				.perform(get("/user/list"))
				.andExpect(model().attribute("users", users))
				.andExpect(view().name("user/list"));
		
		verify(userRepositoryMock).findAll();
	}
	
	@Test
	public void showProfileForm() throws Exception {
		annotationConfigSetup(WebMvcConfig.class).build()
				.perform(get("/user/create"))
				.andExpect(model().attribute("profileForm", Matchers.isA(ProfileForm.class)))
				.andExpect(view().name("user/create"));
	}
	
	@Test
	public void create_ProfileFormHasValidationErrors() throws Exception {
		annotationConfigSetup(WebMvcConfig.class).build()
				.perform(post("/user/create")
						.param("email", "email@email.com"))
				.andExpect(model().attributeHasErrors("profileForm"))
				.andExpect(view().name("user/create"));
	}
	
	@Test
	public void create() throws Exception {
		annotationConfigSetup(WebMvcConfig.class).build()
				.perform(post("/user/create")
						.param("username", "username")
						.param("password", "1Qaz2wsx")
						.param("confirmedPassword", "1Qaz2wsx")
						.param("email", "email@email.com"))
				.andExpect(model().hasNoErrors())
				.andExpect(flash().attributeExists("message"))
				.andExpect(redirectedUrl("list"));
		
		UserRepository userRepositoryMock = WebMvcConfig.userRepositoryMockInstance();
		verify(userRepositoryMock).save(any(User.class));
	}
	
	
	@Configuration
	@EnableWebMvc
	public static class WebMvcConfig {
		
		private static UserRepository userRepositoryMockInstance;
		
		static void initMocks() {
			userRepositoryMockInstance = Mockito.mock(UserRepository.class);
		}
		
		static UserRepository userRepositoryMockInstance() {
			return userRepositoryMockInstance;
		}

		@Bean
		public UserRepository userRepository() {
			return userRepositoryMockInstance;
		}
		
		@Bean
		public UserController userController() {
			return new UserController(userRepository());
		}
	}
}

