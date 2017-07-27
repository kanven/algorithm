package com.kanven.algorithm.serialize.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kanven.algorithm.serialize.Serializer;

/**
 * JAVA自身序列化实现
 * 
 * @author kanven
 * 
 */
public class JavaSerializer implements Serializer {

	private static final Logger log = LoggerFactory.getLogger(JavaSerializer.class);

	@Override
	public byte[] serialize(Object o) {
		ObjectOutputStream objectOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(o);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			return bytes;
		} catch (IOException e) {
			log.error("java 序列化出现异常！", e);
			return null;
		} finally {
			try {
				if (null != byteArrayOutputStream) {
					byteArrayOutputStream.close();
				}
			} catch (IOException e) {
				log.error("序列化IO关闭出现异常！", e);
			}
			try {
				if (null != objectOutputStream) {
					objectOutputStream.close();
				}
			} catch (IOException ex) {
				log.error("序列化IO关闭出现异常！", ex);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserializ(byte[] bts) {
		if (bts == null || bts.length <= 0) {
			return null;
		}
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(bts);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			T result = (T) objectInputStream.readObject();
			return result;
		} catch (IOException e) {
			log.error("java 反序列化出现异常！", e);
			return null;
		} catch (ClassNotFoundException e) {
			log.error("java 反序列化出现异常！", e);
			return null;
		} finally {
			try {
				if (null != byteArrayInputStream) {
					byteArrayInputStream.close();
				}
			} catch (IOException e) {
				log.error("反序列化IO关闭异常！", e);
			}
			try {
				if (null != objectInputStream) {
					objectInputStream.close();
				}
			} catch (Exception ex) {
				log.error("反序列化IO关闭异常！", ex);
			}
		}
	}

}
