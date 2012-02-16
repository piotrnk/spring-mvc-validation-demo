package com.github.kolorobot.web.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.github.kolorobot.web.validation.SamePasswords;

/**
 * Used for wizard-like account creation
 */
@SamePasswords(message = "{validation.profile.samePasswords}", groups = { AccountForm.AccountStepTwo.class })
public class AccountForm implements PasswordAware {

	public interface AccountStepOne {
	}

	public interface AccountStepTwo {
	}
	
	@NotNull(message = "{validation.profile.notNullUsername}", groups = { AccountStepOne.class })
	private String username;

	@Email(message = "{validation.profile.email}", groups = { AccountStepOne.class })
	@NotNull(message = "{validation.profile.notNullEmail}", groups = { AccountStepOne.class })
	private String email;

	@NotNull(message = "{validation.profile.notNullPassword}", groups = { AccountStepTwo.class })
	private String password;

	@NotNull(message = "{validation.profile.notNullConfirmedPassword}", groups = { AccountStepTwo.class })
	private String confirmedPassword;

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
