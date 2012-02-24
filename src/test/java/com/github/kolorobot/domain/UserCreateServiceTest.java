package com.github.kolorobot.domain;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.text.MessageFormat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hibernate.validator.method.*;
import org.hibernate.validator.method.MethodConstraintViolation.Kind;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@ContextConfiguration(classes = {UserCreateServiceTest.Config.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserCreateServiceTest {
	
	@Autowired
	private UserCreateService service;
	
	@Autowired
	private UserRepository userRepositoryMock;
	
	@Rule
	public ExpectedException error = ExpectedException.none();
	
	@Test
	public void createUser_3ArgumentsNull_Has3ContraintViolations() {
		error.expect(new HasContraintViolations(3));
		error.expect(new ParameterViolation(0, "may not be empty"));
		error.expect(new ParameterViolation(1, "may not be empty"));
		error.expect(new ParameterViolation(2, "may not be empty"));
		
		service.createUser(null, null, null);
	}
	
	@Test
	public void createUser_2ArgumentsNull_Has2ContraintViolations() {
		error.expect(new HasContraintViolations(2));
		error.expect(new ParameterViolation(0, "may not be empty"));
		error.expect(new ParameterViolation(1, "may not be empty"));
		
		service.createUser(null, null, "x");
	}
	
		
	@Test
	public void createUser_EmailIsBlank_Has1ContraintViolations() {
		error.expect(new HasContraintViolations(1));
		error.expect(new ParameterViolation(0, "may not be empty"));
		
		service.createUser("", "x", "x");
	}
	
	@Test
	public void createUser_EmailIsInvalid_Has1ContraintViolations() {
		error.expect(new HasContraintViolations(1));
		error.expect(new ParameterViolation(0, "not a well-formed email address"));
		
		service.createUser("x", "x", "x");
	}
	
	@Test
	public void createUser_ReturnValueIsNull_Has1ContraintViolations() {
		when(userRepositoryMock.save(any(User.class))).thenReturn(null);
		
		error.expect(new HasContraintViolations(1));
		error.expect(new ReturnValueViolation("may not be null"));

		service.createUser("user@domain.com", "x", "x");
		
		verify(userRepositoryMock).save(null);
	}
	
	@Test
	public void createUser() {
		when(userRepositoryMock.save(any(User.class))).thenReturn(new User());
		
		service.createUser("user@domain.com", "x", "x");
		
		verify(userRepositoryMock).save(any(User.class));
	}
	
	@Configuration
	public static class Config {
		
		@Bean
		public MethodValidationPostProcessor methodValidationPostProcessor() {
			return new MethodValidationPostProcessor();
		}
		
		@Bean
		public UserRepository userRepository() {
			return Mockito.mock(UserRepository.class);
		}
		
		@Bean
		public UserCreateService userCreateService() {
			return new UserCreateService();
		}
	}
	
	//
	// Method constraint violation verification
	//
	
	public class HasContraintViolations extends TypeSafeMatcher<MethodConstraintViolationException> {
		
		private int constraintViolationCount;
		
		public HasContraintViolations(int constraintViolationCount) {
			this.constraintViolationCount = constraintViolationCount;
		}

		@Override
		public boolean matchesSafely(MethodConstraintViolationException e) {
			return e.getConstraintViolations().size() == constraintViolationCount;
		}

		public void describeTo(Description description) {
			description.appendText(MessageFormat.format("has {0} contraint violation", constraintViolationCount));
		}
	}
	
	public class ParameterViolation extends TypeSafeMatcher<MethodConstraintViolationException> {
		
		private int index;
		private String substring;
		
		public ParameterViolation(int index, String substring) {
			this.index = index;
			this.substring = substring;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public boolean matchesSafely(MethodConstraintViolationException e) {
			for(MethodConstraintViolation c : e.getConstraintViolations()) {
				if(Kind.PARAMETER == c.getKind() && c.getParameterIndex() == index) {					
					return c.getMessage().contains(substring);
				}
			}
			return false;
		}

		public void describeTo(Description description) {
			description.appendText(MessageFormat.format("has no contraint violation for parameter {0} with message containing '{1}'", index, substring));
		}
	}
	
	public class ReturnValueViolation extends TypeSafeMatcher<MethodConstraintViolationException> {
		
		private String substring;
		
		public ReturnValueViolation(String substring) {
			this.substring = substring;
		}

		@Override
		@SuppressWarnings("rawtypes")
		public boolean matchesSafely(MethodConstraintViolationException e) {
			for(MethodConstraintViolation c : e.getConstraintViolations()) {
				if(Kind.RETURN_VALUE == c.getKind()) {					
					return c.getMessage().contains(substring);
				}
			}
			return false;
		}

		public void describeTo(Description description) {
			description.appendText(MessageFormat.format("has no contraint violation for return value with message containing '{0}'", substring));
		}
	}
	
}
