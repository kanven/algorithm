package com.kanven.algorithm.io.codec.gzip;

import java.io.IOException;

import com.kanven.algorithm.io.StreamInput;

public class GZipStreamInput extends StreamInput {

	private GZipStreamInput input;

	public GZipStreamInput(StreamInput input) {
		this.input = new GZipStreamInput(input);
	}

	@Override
	public byte readByte() throws IOException {
		return input.readByte();
	}

	@Override
	public int read() throws IOException {
		return input.read();
	}

	@Override
	public void readBytes(byte[] b, int offset, int len) throws IOException {
		input.read(b, offset, len);
	}

}
