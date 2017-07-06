package com.kanven.algorithm.io;

import java.io.IOException;

/**
 * 统计写入字节数
 * 
 * @author kanven
 *
 */
public class NoopStreamOutput extends StreamOutput {

	private int count = 0;

	public int getCount() {
		return count;
	}

	@Override
	public void writeByte(byte b) throws IOException {
		count += 1;
	}

	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException {
		count += length;
	}

	@Override
	public void reset() throws IOException {
		count = 0;
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() throws IOException {
		
	}

}
