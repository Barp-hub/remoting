package com.btime.guava;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class GuavaTest {

	private final EventBus eventBus = new EventBus();

	@Before
	public void before() {
		eventBus.register(new Object() {

			@Subscribe
			public void lister(Message message) {
				System.out.println("监听到：" + message.getId());
			}

		});
	}

	@Test
	public void publish() throws IOException {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10; j++) {
					Message message = new Message();
					message.setId("线程:" + Thread.currentThread().getName() + "  i:" + j);
					eventBus.post(message);

					try {
						Thread.sleep(Double.valueOf(Math.random() * 1000).intValue());
					} catch (Exception e) {
					}
				}
			}).start();
		}
		System.in.read();
	}

}

class Message {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}