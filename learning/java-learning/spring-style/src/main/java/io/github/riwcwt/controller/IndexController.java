package io.github.riwcwt.controller;

import java.util.Enumeration;

import javax.servlet.ServletConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletConfigAware;

@Controller
public class IndexController implements ServletConfigAware {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		logger.info(servletConfig.getInitParameter("tomcat.name"));
		Enumeration<String> names = servletConfig.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			logger.info(name + " : " + servletConfig.getInitParameter(name));
		}
	}

}
