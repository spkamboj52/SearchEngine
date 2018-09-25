package com.keyoperations;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.fileconversion.TextFile;

public class StringMatchFile {
	
	   private static File FILE_NAME = new File("ConvertedFiles");
	   private static String filePath = FILE_NAME.getAbsolutePath();
	   private final int R;       // the radix
	   private int[][] dfa;       // the KMP automoton
	   private String pat;        // or the pattern string

	   // create the DFA from a String
	   public StringMatchFile(String pat) {
	       this.R = 256;
	       this.pat = pat;

	       // build DFA from pattern
	       int M = pat.length();
	       dfa = new int[R][M]; 
	       dfa[pat.charAt(0)][0] = 1; 
	       for (int X = 0, j = 1; j < M; j++) {
	           for (int c = 0; c < R; c++) 
	               dfa[c][j] = dfa[c][X];     // Copy mismatch cases. 
	           dfa[pat.charAt(j)][j] = j+1;   // Set match case. 
	           X = dfa[pat.charAt(j)][X];     // Update restart state. 
	       } 
	   } 

	   // return offset of first match; N if no match
	   public int searchFile(String txt) {

	       int M = pat.length();
	       int N = txt.length();
	       int i, j;
	       for (i = 0, j = 0; i < N && j < M; i++) {
	           j = dfa[txt.charAt(i)][j];
	       }
	       if (j == M) return i - M;    // found
	       return N;                    // not found
	   }
	   
	   public int search(char[] text) {

	       // simulate operation of DFA on text
	       int M = pat.length();
	       int N = text.length;
	       int i, j;
	       for (i = 0, j = 0; i < N-1 && j < M; i++) {
	           j = dfa[text[i]][j];
	       }
	       if (j == M) return i - M;    // found
	       return N;                    // not found
	   }
	   
	   public Map<String,Integer> search(){
		   Map<String,Integer> mFile = null;
		   try {			   
		   File[] listFiles = null;
		   String textFile = null,extensionFile = null;
		   Integer counter = null;
		   if(FILE_NAME.isDirectory()) {
			   mFile = new HashMap<>();
			   counter = null;
			   listFiles = FILE_NAME.listFiles();
			   for (File file : listFiles) {
				   String fileName = file.getName(); 
				   String filePath = file.getAbsolutePath();
				   
				   TextFile txt = new TextFile();
				   textFile = txt.textFile(filePath);				   
				   counter = MatchString(pat, textFile);
				   if(counter!=0) {
					   mFile.put(fileName, counter);
				   }
			   }
			   
		   }
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
		   return mFile;
	   }
	   
	   
	   public Map<String,Integer> searchCustom(String patteren,String fileName) throws Exception{
		   
		   this.pat = patteren;
		   TextFile textF = new TextFile();
		   String text = textF.textFile(fileName);
		   Map<String,Integer> mFile = null;
		   int counter = 0;
		   try {			   
			   mFile = new HashMap<>();
			   counter = MatchString(patteren, text);
			   if(counter!=0) {
				   mFile.put(fileName, counter);
			   }
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
		   return mFile;
	   }
	   
	   
	   public static Integer MatchString(String pattern, String text) {
		   		int counter = 0;
				int offsetPos = 0, searchPos = 0;
				int patLength = pattern.length();
				String subStr;
				StringMatchFile match = new StringMatchFile(pattern);
				while ((offsetPos <= (text.length() + patLength - 1))) {
					subStr = text.substring(offsetPos);
					searchPos = match.searchFile(subStr);
					//searchPos = match.search(subStr.toCharArray());
					if (searchPos == subStr.length())
						break;
					counter++;
					offsetPos = offsetPos + searchPos + patLength;
				}
				
				return counter;
			}
}
