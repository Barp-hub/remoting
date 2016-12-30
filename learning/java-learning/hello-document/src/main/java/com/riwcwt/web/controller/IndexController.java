package com.riwcwt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riwcwt.application.entity.Application;
import com.riwcwt.application.service.ApplicationService;
import com.riwcwt.web.model.User;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private ApplicationService applicationService = null;

	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> index() {
		return ResponseEntity.status(HttpStatus.OK).body("成功");
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Model model) {
		logger.info("请求成功！");
		model.addAttribute("message", "成功");
		return "hello";
	}

	@ResponseBody
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public ResponseEntity<User> apply(@Valid @RequestBody User user, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@ResponseBody
	@RequestMapping(value = "/session", method = RequestMethod.POST)
	public ResponseEntity<String> session(HttpServletRequest request) {
		request.getSession().setAttribute("session", "session");
		return ResponseEntity.status(HttpStatus.OK).body("session设置成功");
	}

	@ResponseBody
	@RequestMapping(value = "/application", method = RequestMethod.GET)
	public ResponseEntity<Application> application(@RequestParam(value = "id", required = true) Integer id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.applicationService.getApplication(id));
	}
}
