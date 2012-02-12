package com.github.kolorobot.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQueries ({
	@NamedQuery(name = "User.findByName", query = "select u from User u where u.name = :name"),
	@NamedQuery(name = "User.findAll", query = "select u from User u ")
})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	@NotNull
	private String name;
	
	@Column
	@NotNull
	@Email
	private String email;
	
	@Column
	@NotNull
	private String password;

	protected Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
