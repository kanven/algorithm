package com.kanven.algorithm.ssl.db;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class KeyStoreManager {

	/**
	 * 获取密钥存储器
	 * 
	 * @param password
	 *            密码
	 * @param path
	 *            密钥文件路径
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private static KeyStore getKeyStore(String password, String path)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore store = KeyStore.getInstance("JKS");
		ClassLoader cl = KeyStoreManager.class.getClassLoader();
		InputStream in = cl.getResourceAsStream(path);
		store.load(in, password.toCharArray());
		in.close();
		return store;
	}

	/**
	 * 获取密钥存储器
	 * 
	 * @param password
	 *            密码
	 * @param path
	 *            密钥文件路径
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	public static KeyManager[] getKeyManagers(String password, String path) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		factory.init(getKeyStore(password, path), password.toCharArray());
		return factory.getKeyManagers();
	}

	/**
	 * 
	 * @param password
	 *            密码
	 * @param path
	 *            对方受信密钥文件路径
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static TrustManager[] getTrustManagers(String password, String path)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(getKeyStore(password, path)); // 受信任密钥使用私钥
		return tmf.getTrustManagers();
	}

	public static SSLContext getSSLContext(KeyManager[] keyManagers, TrustManager[] trustManagers)
			throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException,
			UnrecoverableKeyException, KeyManagementException {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagers, trustManagers, null);
		return context;
	}

}
