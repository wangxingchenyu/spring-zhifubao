package com.zhileiedu.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: 王志雷
 * @Date: 2020/5/9 12:26
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public String test(Model model){
		return "index";
	}


}
