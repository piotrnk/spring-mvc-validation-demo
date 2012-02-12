package com.github.kolorobot.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.kolorobot.domain.UserRepository;
import com.github.kolorobot.web.form.ProfileForm;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, ProfileForm> {

	@Autowired
	protected UserRepository userRepository;

	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
	}

	@Override
	public boolean isValid(ProfileForm value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		if (value.getUsername() == null) {
			return true;
		}
		return !userRepository.hasUser(value.getUsername());
	}
}
