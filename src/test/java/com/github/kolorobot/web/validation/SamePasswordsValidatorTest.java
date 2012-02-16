package com.github.kolorobot.web.validation;

import junit.framework.Assert;

import org.junit.Test;

import com.github.kolorobot.web.form.PasswordAware;

public class SamePasswordsValidatorTest {

	private SamePasswordsValidator validator = new SamePasswordsValidator();

	@Test
	public void isValid_ConfirmedPasswordEqualToPassword_ReturnsTrue() {
		// arrange
		PasswordAware passwordAware = new MyPasswordAware("x", "x");
		// act
		boolean result = validator.isValid(passwordAware, null);
		// assert
		Assert.assertTrue(result);

	}

	@Test
	public void isValid_ConfirmedPasswordNotEqualToPassword_ReturnsFalse() {
		// arrange
		PasswordAware passwordAware = new MyPasswordAware("x", "y");
		// act
		boolean result = validator.isValid(passwordAware, null);
		// assert
		Assert.assertFalse(result);

	}

	@Test
	public void isValid_NullPasswordAndNotNullConfirmedPassword_ReturnsFalse() {
		// arrange
		PasswordAware passwordAware = new MyPasswordAware(null, "x");
		// act
		boolean result = validator.isValid(passwordAware, null);
		// assert
		Assert.assertFalse(result);

	}

	@Test
	public void isValid_NotNullPasswordAndNullConfirmedPassword_ReturnsFalse() {
		// arrange
		PasswordAware passwordAware = new MyPasswordAware("x", null);
		// act
		boolean result = validator.isValid(passwordAware, null);
		// assert
		Assert.assertFalse(result);

	}

	private final class MyPasswordAware implements PasswordAware {

		private final String password;
		private final String confirmedPassword;

		private MyPasswordAware(String password, String confirmedPassword) {
			this.password = password;
			this.confirmedPassword = confirmedPassword;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getConfirmedPassword() {
			return confirmedPassword;
		}
	}

}
