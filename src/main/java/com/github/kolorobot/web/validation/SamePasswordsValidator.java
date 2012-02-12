package com.github.kolorobot.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.kolorobot.web.form.PasswordAware;

public class SamePasswordsValidator implements ConstraintValidator<SamePasswords, PasswordAware> {

	@Override
	public void initialize(SamePasswords constraintAnnotation) {}

	@Override
	public boolean isValid(PasswordAware value, ConstraintValidatorContext context) {
		if(value.getConfirmedPassword() == null) {
			return true;
		}
		return value.getConfirmedPassword().equals(value.getPassword());
	}
}
