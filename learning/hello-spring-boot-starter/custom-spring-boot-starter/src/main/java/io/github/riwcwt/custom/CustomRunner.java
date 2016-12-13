package io.github.riwcwt.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;

public class CustomRunner implements CommandLineRunner {

	@Autowired
	private AbstractApplicationContext applicationContext;

	public void run(String... args) throws Exception {

	}

}
