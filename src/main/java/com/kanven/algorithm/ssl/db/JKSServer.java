package com.kanven.algorithm.ssl.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;

public class JKSServer {

	public static void main(String[] args) throws UnrecoverableKeyException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		String password = "123456";
		String sp = "ssl/server.jks";
		String tcp = "ssl/client.jks";
		SSLContext context = KeyStoreManager.getSSLContext(KeyStoreManager.getKeyManagers(password, sp),
				KeyStoreManager.getTrustManagers(password, tcp));
		ServerSocketFactory factory = context.getServerSocketFactory();
		ServerSocket server = factory.createServerSocket(9090);
		Socket socket = server.accept();
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		out.write("Hello".getBytes());
		out.flush();
		byte[] buf = new byte[1024];
		int len = in.read(buf);
		System.out.println(new String(buf,0,len));
		socket.close();
		server.close();
	}

}
