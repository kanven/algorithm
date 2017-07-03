package com.kanven.algorithm.ssl.single;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;

public class JKSServer extends AbstractSSLImpl {

	private int port;

	private ServerSocket server;

	public JKSServer(String password, String path) {
		super(password, path);
	}

	public void bind(int port) throws Exception {
		SSLContext context = getSSLContext();
		ServerSocketFactory factory = context.getServerSocketFactory();
		this.port = port;
		server = factory.createServerSocket(port);
	}

	@Override
	public void close() {
		if (server != null && !server.isClosed()) {
			try {
				server.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public byte[] read(int buffered) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(String msg) {
		// TODO Auto-generated method stub

	}

	public int getPort() {
		return port;
	}
	
	public static void main(String[] args) throws Exception {
		JKSServer server = new JKSServer("123456", "ssl/server.jks");
		server.bind(9090);
		Socket socket = server.server.accept();
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();
		output.write("Hello".getBytes());
		output.flush();
		byte[] buf = new byte[1024];
		int len = input.read(buf);
		System.out.println(new String(buf,0,len));
		socket.close();
		output.close();
		input.close();
		server.close();
	}

}
