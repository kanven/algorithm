package com.kanven.algorithm.io.codec.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import com.kanven.algorithm.io.StreamOutput;

/**
 * GZIP压缩数据
 * 
 * @author kanven
 *
 */
public class GZipStreamOutput extends StreamOutput {

	private GZIPOutputStream output;

	public GZipStreamOutput(OutputStream output) throws IOException {
		this.output = new GZIPOutputStream(output);
	}

	@Override
	public void writeByte(byte b) throws IOException {
		output.write(b);
	}

	@Override
	public void writeBytes(byte[] b, int offset, int length) throws IOException {
		output.write(b, offset, length);
	}

	@Override
	public void flush() throws IOException {
		output.flush();
	}

	@Override
	public void close() throws IOException {
		output.finish();
		output.close();
	}

}
