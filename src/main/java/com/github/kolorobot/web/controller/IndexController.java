package com.github.kolorobot.web.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

	@RequestMapping(value = "/")
	public String index(Model model) {
		return "index";
	}

}
