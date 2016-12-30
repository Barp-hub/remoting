package com.machine;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger=LoggerFactory.getLogger(Main.class);
	
	private static volatile boolean running = true;
	
	public static void main(String[] args) throws UnknownHostException {

		shutdown();
	}

	public static void shutdown(){
		 Runtime.getRuntime().addShutdownHook(new Thread() {
             public void run() {
                     synchronized (Main.class) {
                    	 logger.info("关闭中...");
                         running = false;
                         Main.class.notify();
                     }
             }
         });
		
        synchronized (Main.class) {
            while (running) {
                try {
                	logger.info("等待中...");
                    Main.class.wait();
                } catch (Throwable e) {
                }
            }
        }
        logger.info("程序关闭！");
	}
	
	public static void getHost() throws UnknownHostException {
		System.out.println("host ip:" + InetAddress.getLocalHost().getHostAddress());
		System.out.println("host name:" + InetAddress.getLocalHost().getHostName());

		int busyTime = 10;
		int idleTime = 2;
		long startTime = 0;
		while (true) {
			startTime = System.currentTimeMillis();
			while ((System.currentTimeMillis() - startTime) <= busyTime)
				;
			try {
				Thread.sleep(idleTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
