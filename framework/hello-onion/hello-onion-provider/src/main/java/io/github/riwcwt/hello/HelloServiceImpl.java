package io.github.riwcwt.hello;

import io.github.riwcwt.proxy.annotation.RemotingService;

@RemotingService(value = HelloService.class, version = "1.0")
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "hello, " + name + "!";
	}

}
