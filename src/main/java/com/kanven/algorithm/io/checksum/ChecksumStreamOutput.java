package com.kanven.algorithm.io.checksum;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.kanven.algorithm.io.StreamOutput;

public class ChecksumStreamOutput extends StreamOutput {

	private StreamOutput output;

	private Checksum checksum;

	public ChecksumStreamOutput(StreamOutput output) {
		this.output = output;
		this.checksum = new CRC32();
	}

	@Override
	public void writeByte(byte b) throws IOException {
		output.writeByte(b);
		checksum.update(b);
	}

	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException {
		output.writeBytes(b, offset, length);
		checksum.update(b, offset, length);
	}

	@Override
	public void flush() throws IOException {
		output.flush();
	}

	@Override
	public void close() throws IOException {
		output.close();
	}

	@Override
	public void reset() throws IOException {
		output.reset();
		checksum.reset();
	}

	public long getChecksum() {
		return this.checksum.getValue();
	}

}
