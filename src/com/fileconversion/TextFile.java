package com.fileconversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextFile {
	public String textFile(String filePath) throws Exception {
		String page = null;
		try {
			FileReader fr = new FileReader(new File(filePath));
			BufferedReader br = new BufferedReader(fr);

			String line = null;
		    StringBuilder stringBuilder = new StringBuilder();		    
		    while((line = br.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(" ");
	        }
		    page = stringBuilder.toString();
		    page = page.trim();
		} catch (Exception e) {
			throw e;
			// TODO Auto-generated catch block
		}
		
		return page;
	}
}
