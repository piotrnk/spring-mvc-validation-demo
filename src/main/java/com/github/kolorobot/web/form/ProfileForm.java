package com.github.kolorobot.web.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.github.kolorobot.domain.User;
import com.github.kolorobot.web.validation.*;

@UniqueUsername(message = "{validation.profile.uniqueUsername}")
@SamePasswords(message = "{validation.profile.samePasswords}")
public class ProfileForm implements PasswordAware {

	@NotNull(message = "{validation.profile.notNullUsername}", groups = { User.class })
	private String username;

	private String password;

	@NotNull
	private String confirmedPassword;

	@Email
	@NotNull
	private String email;

	@Override
	@NotNull(message = "{validation.profile.notNullPassword}")
	@StrongPassword(message = "{validation.profile.strongPassword}")
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
