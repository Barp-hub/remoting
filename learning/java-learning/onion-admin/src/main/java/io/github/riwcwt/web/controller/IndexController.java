package io.github.riwcwt.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.riwcwt.web.model.User;

@Controller
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String, Object> list() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("results", 40);
		List<User> users = new LinkedList<User>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUserId(Long.valueOf(i));
			user.setCreationDate(new Date());
			user.setDescription("测试数据");
			user.setEnable(Byte.valueOf("0"));
			user.setUsername("michael");
			users.add(user);
		}
		result.put("rows", users);
		return result;
	}
}
