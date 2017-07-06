package com.kanven.algorithm.io.checksum;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.kanven.algorithm.io.StreamInput;

public class BufferedChecksumStreamInput extends StreamInput {

	private final StreamInput input;

	private final Checksum digest;

	public BufferedChecksumStreamInput(StreamInput input) {
		this.input = input;
		digest = new BufferedChecksum(new CRC32());
	}

	@Override
	public byte readByte() throws IOException {
		final byte b = input.readByte();
		digest.update(b);
		return b;
	}

	@Override
	public void readBytes(byte[] b, int offset, int len) throws IOException {
		input.readBytes(b, offset, len);
		digest.update(b, offset, len);
	}

	@Override
	public int read() throws IOException {
		return readByte() & 0xFF;
	}

	@Override
	public void close() throws IOException {
		input.close();
	}

	@Override
	public synchronized void reset() throws IOException {
		input.reset();
		digest.reset();
	}

}
