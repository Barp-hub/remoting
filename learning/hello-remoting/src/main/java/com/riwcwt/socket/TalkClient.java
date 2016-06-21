package com.riwcwt.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TalkClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket server = new Socket(InetAddress.getLocalHost(), 4700);
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter out = new PrintWriter(server.getOutputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = reader.readLine();
			out.println(line);
			out.flush();
			if (line.equals("end")) {
				break;
			}
			System.out.println(in.readLine());
		}
		server.close();

	}

}
