package com.github.kolorobot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.kolorobot.domain.User;
import com.github.kolorobot.domain.UserCreateService;
import com.github.kolorobot.web.form.*;
import com.github.kolorobot.web.form.AccountForm.AccountStepOne;
import com.github.kolorobot.web.form.AccountForm.AccountStepTwo;
import com.github.kolorobot.web.message.MessageFactory;

@Controller
@SessionAttributes("accountForm")
@RequestMapping("account")
public class AccountRegistrationWizardController {

	@Autowired
	private UserCreateService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String handleHttpSessionRequiredException(HttpSessionRequiredException exception) {		
		return "redirect:stepOne";
	}

	@RequestMapping("stepOne")
	public AccountForm showStepOne() {
		return new AccountForm();
	}

	@RequestMapping(value = "stepOne", method = RequestMethod.POST)
	public String stepOne(@Validated(AccountStepOne.class) @ModelAttribute AccountForm accountForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/stepOne";
		}
		return "redirect:stepTwo";
	}

	@RequestMapping("stepTwo")
	public String showStepTwo(@Validated(AccountStepOne.class) @ModelAttribute AccountForm accountForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:stepOne";
		}
		return "account/stepTwo";
	}

	@RequestMapping(value = "stepTwo", method = RequestMethod.POST)
	public String stepTwo(@Validated(AccountStepTwo.class) @ModelAttribute AccountForm accountForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/stepTwo";
		}
		return "redirect:summary";
	}

	@RequestMapping("summary")
	public String showSummary(@Validated({AccountStepOne.class, AccountStepTwo.class}) @ModelAttribute AccountForm accountForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", MessageFactory.createErrorMessage("account.wizard.error"));
			return "redirect:stepOne";
		}
		return "account/summary";
	}

	@Transactional
	@RequestMapping("create")
	public String createAccount(@Validated({AccountStepOne.class, AccountStepTwo.class}) @ModelAttribute AccountForm accountForm, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", MessageFactory.createErrorMessage("account.wizard.error"));
			return "redirect:stepOne";
		}
		User user = createUser(accountForm);
		sessionStatus.setComplete();
		redirectAttributes.addFlashAttribute("message", MessageFactory.createInfoMessage("account.created", user.getName()));
		return "redirect:../user/list";
	}

	private User createUser(AccountForm accountForm) {
		return service.createUser(accountForm.getEmail(), accountForm.getUsername(), accountForm.getPassword());
	}
}
