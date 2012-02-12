package com.github.kolorobot.web.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.kolorobot.domain.User;
import com.github.kolorobot.domain.UserRepository;
import com.github.kolorobot.web.form.*;
import com.github.kolorobot.web.form.AccountForm.AccountStepOne;
import com.github.kolorobot.web.form.AccountForm.AccountStepTwo;
import com.github.kolorobot.web.message.MessageFactory;

@Controller
@SessionAttributes("accountForm")
@RequestMapping("account")
public class AccountRegistrationWizardController {

	@Resource
	private UserRepository userRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("stepOne")
	public AccountForm stepOneAccountRegistration() {
		return new AccountForm();
	}

	@RequestMapping(value = "stepOne", method = RequestMethod.POST)
	public String stepOneAccountRegistration(@ModelAttribute AccountForm accountForm, BindingResult bindingResult, Model model) {
//		if (isNotValid(accountForm, bindingResult, AccountStepOne.class)) {
//			return "account/stepOne";
//		}
		return "redirect:stepTwo";
	}

	@RequestMapping("stepTwo")
	public void stepTwoAccountRegistration() {

	}

	@RequestMapping(value = "stepTwo", method = RequestMethod.POST)
	public String stepTwoAccountRegistration(@ModelAttribute AccountForm accountForm, BindingResult bindingResult) {
//		if (isNotValid(accountForm, bindingResult, AccountStepTwo.class)) {
//			return "account/stepTwo";
//		}

		return "redirect:summary";
	}

	@RequestMapping("summary")
	public void summaryAccountRegistration(@ModelAttribute AccountForm accountForm, Model model) {
		model.addAttribute(accountForm);
	}

	@Transactional
	@RequestMapping("finish")
	public String finishAccountRegistration(@ModelAttribute AccountForm accountForm, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
//		if (isNotValid(accountForm, bindingResult, AccountStepOne.class, AccountStepTwo.class)) {
//			redirectAttributes.addAttribute("message", MessageFactory.createErrorMessage("Not all fields were filed properly, try again!"));
//			return "redirect:stepOne";
//		}
		User user = createUser(accountForm);
		sessionStatus.setComplete();
		redirectAttributes.addAttribute("message", MessageFactory.createInfoMessage("account.created", user.getName()));
		return "redirect:../user/list";
	}

	private User createUser(AccountForm accountForm) {
		User user = new User();
		user.setEmail(accountForm.getEmail());
		user.setName(accountForm.getUsername());
		user.setPassword(accountForm.getUsername());
		userRepository.save(user);
		return user;
	}
}
