package com.github.kolorobot.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.kolorobot.domain.User;
import com.github.kolorobot.domain.UserRepository;
import com.github.kolorobot.web.form.ProfileForm;
import com.github.kolorobot.web.message.MessageFactory;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private UserRepository userRepository;
	
	public UserController() {

	}
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("list")
	public void userList(Model model, HttpSession session) {
		model.addAttribute("users", userRepository.findAll());
	}
	
	@RequestMapping("create")
	public void profileForm(Model model) {
		model.addAttribute(new ProfileForm());
	}
	
	@Transactional
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ProfileForm profileForm, Errors errors, RedirectAttributes redirectAttrs) {
		if(errors.hasErrors()) {
			return "user/create";
		}
		
		User user = new User();
		user.setEmail(profileForm.getEmail());
		user.setName(profileForm.getUsername());
		user.setPassword(profileForm.getUsername());
		userRepository.save(user);
		
		redirectAttrs.addFlashAttribute("message", MessageFactory.createInfoMessage("account.created", user.getName()));
		return "redirect:list";
	}
}
