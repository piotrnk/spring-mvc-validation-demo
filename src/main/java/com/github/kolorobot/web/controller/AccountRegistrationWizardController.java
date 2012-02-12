package com.github.kolorobot.web.controller;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	public String stepOneAccountRegistration(@Validated(AccountStepOne.class) @ModelAttribute AccountForm accountForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "account/stepOne";
		}
		return "redirect:stepTwo";
	}

	@RequestMapping("stepTwo")
	public void stepTwoAccountRegistration() {

	}

	@RequestMapping(value = "stepTwo", method = RequestMethod.POST)
	public String stepTwoAccountRegistration(@Validated(AccountStepTwo.class) @ModelAttribute AccountForm accountForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "account/stepTwo";
		}

		return "redirect:summary";
	}

	@RequestMapping("summary")
	public void summaryAccountRegistration(@ModelAttribute AccountForm accountForm, Model model) {
		model.addAttribute(accountForm);
	}

	@Transactional
	@RequestMapping("create")
	public String finishAccountRegistration(@Validated({AccountStepOne.class, AccountStepTwo.class}) @ModelAttribute AccountForm accountForm, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
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
		User user = new User();
		user.setEmail(accountForm.getEmail());
		user.setName(accountForm.getUsername());
		user.setPassword(accountForm.getUsername());
		userRepository.save(user);
		return user;
	}
}
