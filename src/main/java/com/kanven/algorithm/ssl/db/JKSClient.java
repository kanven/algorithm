package com.kanven.algorithm.ssl.db;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;

/**
 * 客服端简单测试类
 * 
 * @author kanven
 *
 */
public class JKSClient {

	public static void main(String[] args) throws Exception {
		String password = "123456";
		String cp = "ssl/client.jks";
		String tsp = "ssl/server.jks";
		SSLContext context = KeyStoreManager.getSSLContext(KeyStoreManager.getKeyManagers(password, cp),
				KeyStoreManager.getTrustManagers(password, tsp));
		SocketFactory factory = context.getSocketFactory();
		Socket socket = factory.createSocket("127.0.0.1", 9090);
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		out.write("alert".getBytes());
		out.flush();
		byte[] buffered = new byte[1024];
		int len = in.read(buffered);
		System.out.println(new String(buffered, 0,len));
		socket.close();
	}

}
