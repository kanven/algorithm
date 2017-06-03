package com.kanven.algorithm.io;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.IOException;

public class DataOutputStreamOutput extends StreamOutput {

	private DataOutput output;

	public DataOutputStreamOutput(DataOutput output) {
		this.output = output;
	}

	@Override
	public void writeByte(byte b) throws IOException {
		output.writeByte(b);
	}

	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException {
		output.write(b, offset, length);
	}

	@Override
	public void close() throws IOException {
		super.close();
		if (output instanceof Cloneable) {
			((Closeable) output).close();
		}
	}

}
