package com.github.kolorobot.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserCreateService {

	@Autowired
	private UserRepository repo;

	@NotNull
	public User createUser(@NotBlank @Email String email, @NotBlank String username, @NotBlank String password) {

		User user = new User();
		user.setEmail(email);
		user.setName(username);
		user.setPassword(password);

		return repo.save(user);
	}

}
