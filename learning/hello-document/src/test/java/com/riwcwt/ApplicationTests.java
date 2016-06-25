package com.riwcwt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.riwcwt.web.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationDocumentTests.class);

	@Autowired
	private WebApplicationContext context = null;

	@Autowired
	private Gson gson = null;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void index() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/")).andReturn();
		logger.info(result.getResponse().getContentAsString());
	}

	@Test
	public void apply() throws Exception {
		User user = new User();
		user.setUsername("admin");
		user.setEmail("admin@qq.com");
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/apply").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(user))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info(result.getResponse().getContentAsString());
	}

}
