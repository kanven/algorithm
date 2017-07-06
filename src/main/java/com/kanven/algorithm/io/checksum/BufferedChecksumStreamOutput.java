package com.kanven.algorithm.io.checksum;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.kanven.algorithm.io.StreamOutput;

public class BufferedChecksumStreamOutput extends StreamOutput {

	private final StreamOutput out;

	private final Checksum digest;

	public BufferedChecksumStreamOutput(StreamOutput output) {
		this.out = output;
		this.digest = new BufferedChecksum(new CRC32());
	}

	public long getChecksum() {
		return this.digest.getValue();
	}

	@Override
	public void writeByte(byte b) throws IOException {
		out.writeByte(b);
		digest.update(b);
	}

	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException {
		out.write(b, offset, length);
		digest.update(b, offset, length);
	}

	@Override
	public void reset() throws IOException {
		out.reset();
		digest.reset();
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

}
