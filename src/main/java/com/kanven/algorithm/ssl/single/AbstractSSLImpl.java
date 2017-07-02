package com.kanven.algorithm.ssl.single;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public abstract class AbstractSSLImpl {

	private String password;

	private String path;

	public AbstractSSLImpl(String password, String path) {
		this.password = password;
		this.path = path;
	}

	/**
	 * 获取密钥存储器
	 * 
	 * @param password
	 *            密码
	 * @param path
	 *            密钥
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private KeyStore getKeyStore()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore store = KeyStore.getInstance("JKS");
		ClassLoader cl = AbstractSSLImpl.class.getClassLoader();
		InputStream in = cl.getResourceAsStream(path);
		store.load(in, password.toCharArray());
		in.close();
		return store;
	}

	public SSLContext getSSLContext() throws NoSuchAlgorithmException, KeyStoreException, CertificateException,
			IOException, UnrecoverableKeyException, KeyManagementException {
		KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		KeyStore store = getKeyStore();
		factory.init(store, password.toCharArray());
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(store); // 受信任密钥使用私钥
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(factory.getKeyManagers(), tmf.getTrustManagers(), null);
		return context;
	}

	public abstract byte[] read(int buffered) throws IOException;

	public abstract void write(String msg) throws IOException;

	public abstract void close();

}
