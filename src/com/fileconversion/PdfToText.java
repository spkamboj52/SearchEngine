package com.fileconversion;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PdfToText {
	
	
	public String pdfToText(String filePath, int pagenum) {
		String page = null;
		try {
		    PdfReader reader = new PdfReader(filePath);
		    page = PdfTextExtractor.getTextFromPage(reader, pagenum);
		   
		    page = page.trim();
		} catch (Exception e) {
		    //e.printStackTrace();
		}
		return page;
	}
	

}
