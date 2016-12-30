package com.riwcwt.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkServer extends Thread {

	private Socket socket = null;

	public TalkServer(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			PrintWriter out = new PrintWriter(this.socket.getOutputStream());

			while (true) {
				String line = in.readLine();
				System.out.println(line);
				out.println("has receive....");
				out.flush();
				if (line.equals("end")) {
					TalkServer.close = true;
					break;
				}
			}
			this.socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		super.run();
	}

	public static boolean close = false;

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(4700);
		while (!TalkServer.close) {
			Socket socket = server.accept();
			TalkServer connection = new TalkServer(socket);
			connection.start();
		}
		server.close();
	}

}
