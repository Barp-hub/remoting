package com.machine;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {

	Logger logger = Logger.getLogger(this.getClass().getName());

	@Test
	public void nullPointer() {

		Integer n = null;

		for (int i = 0; i < 100000; i++) {
			try {
				System.out.println(n.intValue());
			} catch (Exception e) {
				logger.error("error : ", e);
			}
		}
	}

}
