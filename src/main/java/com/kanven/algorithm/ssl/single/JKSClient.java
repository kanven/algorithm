package com.kanven.algorithm.ssl.single;

import java.io.IOException;
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
public class JKSClient extends AbstractSSLImpl {

	private String ip;

	private int port;

	private Socket socket;

	private InputStream in;

	private OutputStream out;

	public JKSClient(String password, String path) {
		super(password, path);
	}

	public void connection(String ip,int port) throws Exception {
		this.ip = ip;
		this.port = port;
		SSLContext context = getSSLContext();
		SocketFactory factory = context.getSocketFactory();
		socket = factory.createSocket(ip, port);
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	public byte[] read(int bufferd) throws IOException {
		byte[] buf = new byte[1024];
		in.read(buf);
		return buf;
	}

	public void write(String msg) throws IOException {
		out.write(msg.getBytes());
		out.flush();
	}

	public void close() {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void main(String[] args) throws Exception {
		JKSClient client = new JKSClient( "123456", "ssl/ks.keystore");
		client.connection("localhost", 9090);
		client.write("alert");
		byte[] buf = client.read(1024);
		System.out.println(new String(buf, "UTF-8"));
		client.close();
	}

}
