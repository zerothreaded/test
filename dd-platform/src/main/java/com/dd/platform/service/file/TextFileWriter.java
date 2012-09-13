package com.dd.platform.service.file;

import java.io.File;
import java.io.FileWriter;

public class TextFileWriter {

	protected File file;
	
	public void write(String fileName, String content) throws Exception {
		file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}

	public String read() throws Exception {
		return "";
	}
	
}
