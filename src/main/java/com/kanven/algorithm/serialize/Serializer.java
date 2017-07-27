package com.kanven.algorithm.serialize;

/**
 * 序列化接口
 * 
 * @author kanven
 * 
 */
public interface Serializer {

	public byte[] serialize(Object o);

	public <T> T deserializ(byte[] bts);

}
