package io.github.riwcwt.discovery;

import java.io.Closeable;
import java.io.IOException;

public class TestingServer implements Closeable {

	private String connectString;

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	@Override
	public void close() throws IOException {

	}

}
