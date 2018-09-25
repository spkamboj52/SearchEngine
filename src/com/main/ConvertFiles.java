package com.main;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;

import com.fileconversion.PdfToText;
import com.itextpdf.text.pdf.PdfReader;
import com.keyoperations.TrieIndex;

public class ConvertFiles {
	
	static String currentCustomFileName;
	static File f = new File("UserFiles");
	static File f1 = new File("ConvertedFiles");
	
	static String outputCustomDirectory = f.getAbsolutePath()+"/";
	static String outputDirectoryLocal = f1.getAbsolutePath()+"/";
	static int file_count=0;
	
	public static void main(String[] args) throws Exception {
		Scanner scan=new Scanner(System.in);
		
		
		String url="";
		String input_number;
		while(true) {
			System.out.println("---------------SEARCH ENGINE---------------");
			System.out.println("\tFunctions: -");
			System.out.println("\t1- Search from a URL.");
			System.out.println("\t2- Enter a location to a folder.");
			System.out.println("\t3- Search from converted files.");
			System.out.println("\t4- Exit.");
			System.out.println("-------------------------------------------");


			input_number=scan.nextLine();
		
			switch(input_number) {
			case "1":
				
				System.out.println("-------------------------------------------");
				System.out.print("Paste a URL to a Webpage here:: ");
				url=scan.nextLine();
				System.out.println("-------------------------------------------");
				long startt=0,endd=0;

				System.out.println("////Scanning the URL....");
				System.out.println("////Converting....");
				startt=System.currentTimeMillis();
				String outcustom=customFile(url);
				endd=System.currentTimeMillis();
				System.out.println("////Converted to text! at "+outcustom);
				System.out.println("Time Taken:"+(endd-startt)+"ms");
				System.out.println("-------------------------------------------");
				String word="";
				while(!word.equalsIgnoreCase("exitsys")) {
					System.out.print("Please enter the word to be searched( or 'exitsys' to exit): ");
					word = scan.nextLine();
					if(!word.equalsIgnoreCase("exitsys")) {
						System.out.println("-------------------------------------------");
						startt=System.currentTimeMillis();
						TrieIndex<Map<String,Integer>> tr = new TrieIndex<>();
						tr.searchCustom(word, outcustom);
						/*endd=System.currentTimeMillis();
						System.out.println("Time Taken:"+(endd-startt)+"ms");*/
					}
				}
				break;
			case "2":
				System.out.println("-------------------------------------------");

				System.out.print("Enter location::");
				String folder_location=scan.nextLine();
				System.out.println("-------------------------------------------");

				folder_location+="/";
				System.out.println("Entered location:::"+folder_location);
				System.out.println("-------------------------------------------");

				
				File input=new File(folder_location);
				if (input.exists()) {
					System.out.println("////Reading Local Files....");
				}
				else {
					System.out.println("////No Files Found in local storage!!");
					System.exit(0);
				}
				File[] retrieved_files=input.listFiles();
				System.out.println("////Total retrieved files::"+retrieved_files.length);
				System.out.println("-------------------------------------------");

				
				System.out.println("////Starting Conversion....");
				long start=System.currentTimeMillis();
				for(int i=0;i<retrieved_files.length;i++) {
					convertToText(retrieved_files[i]);
				}
				long end=System.currentTimeMillis();
				double total=(double)(end-start);
				System.out.println("////Converted "+retrieved_files.length+" Files in "+total+" MilliSeconds!! at "+outputDirectoryLocal);
				System.out.println("////Total text files created:"+file_count);
				break;
			case "3":
				try {
					Scanner s = new Scanner(System.in);
					System.out.println("-------------------------------------------");

					System.out.println("Enter a word you want to search: ");
					System.out.println("or type 'ExitSystem' to end the searching");
					System.out.println("-------------------------------------------");

					String word2 = s.nextLine();
					
					while(!word2.equalsIgnoreCase("exitsystem")) {
						SearchWord.search(word2);
						System.out.println("-------------------------------------------");

						System.out.print("Enter the next word to search: ");
						word2 = s.nextLine();
						System.out.println("-------------------------------------------");

					}
					
					if(word2.equalsIgnoreCase("exitsystem")) {
						System.out.println("Exiting....");
						System.exit(0);
					}
					s.close();			
				}
				catch(Exception e) {
					
				}
				break;
			case "4":
				System.out.println("-------------------------------------------");

				System.out.println("Exiting....");
				System.exit(0);
				
			}

		}
			
	}
	
	private static String customFile(String url) {
		String outp = null;
		try {
			File file=new File(url);
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			String aa = "[^a-zA-Z0-9\\\\s!.,<>/\\\\:;@#$%^&*()]";
			String str=doc.text();
			str = str.replaceAll(aa, " ");
			outp=outputCustomDirectory+file.getName()+".txt";
			PrintWriter pw=new PrintWriter(outp);
			pw.write(str);
			pw.close();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return outp;
		
	}
	
	private static void convertToText(File file) throws Exception {
		try {
			org.jsoup.nodes.Document doc=Jsoup.parse(file,"UTF-8","http://example.com/");
			String str=null;
			PrintWriter pw;
			if(file.getName().endsWith(".html")) {
				String aa = "[^a-zA-Z0-9\\s!.,<>/\\:;@#$%^&*()]";
				str=doc.text();
		    	str = str.replaceAll(aa, " ");
				pw=new PrintWriter(outputDirectoryLocal+file.getName().replaceAll((".html"), ".txt"));
				pw.write(str);
				pw.close();
				file_count++;

			}
			else if(file.getName().endsWith(".htm")) {
				String aa = "[^a-zA-Z0-9\\s!.,<>/\\:;@#$%^&*()]";
				str=doc.text();
				str = str.replaceAll(aa, " ");
				pw=new PrintWriter(outputDirectoryLocal+file.getName().replaceAll((".htm"), ".txt"));
				pw.write(str);
				pw.close();
				file_count++;
			}
			else if(file.getName().endsWith(".txt")) {
				//String aa = "[^a-zA-Z0-9\\s]";
				str=doc.text();
		    //	str = str.replaceAll(aa, "");
				pw=new PrintWriter(outputDirectoryLocal+file.getName());
				pw.write(str);
				pw.close();
				file_count++;
			}
			else if(file.getName().endsWith(".java")) {
				str=doc.text();
				pw=new PrintWriter(outputDirectoryLocal+file.getName().replaceAll((".java"), ".txt"));
				pw.write(str);
				pw.close();
				file_count++;
				
			}
			else if(file.getName().endsWith(".pdf")) {
				PdfToText pdfToText=new PdfToText();
			    
				try {
					PdfReader reader = new PdfReader(file.getAbsolutePath());
					//ArrayList<String> sss=new ArrayList<String>();
					//String[] str2=new String[reader.getNumberOfPages()];
				    //System.out.println(reader.getNumberOfPages());
				    int t=reader.getNumberOfPages();
				   // BufferedWriter bw = null;
				    for(int q=0;q<t;q++) {
				    	int pageNo = q+1;
				    	String page = pdfToText.pdfToText(file.getAbsolutePath(), pageNo);
				    	String fileName = file.getName();
//				    	bw = new BufferedWriter(new FileWriter(new File()));
				    	
				    	
				    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputDirectoryLocal+"Page-"+String.valueOf(pageNo)+"-"+fileName.substring(0, fileName.lastIndexOf('.'))+".txt"), StandardCharsets.UTF_8);

				    	
				    	String aa = "[^a-zA-Z0-9\\s!.,<>/\\:;@#$%^&*()]";
				    	page = page.replaceAll(aa, " ");
				    	writer.write(page);
				    	file_count++;
				    	writer.close();
				    }					    
				    
				    }
				    
				 catch (Exception e) {
				    e.printStackTrace();
				}
				
				
			}
			
			
			
		
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
