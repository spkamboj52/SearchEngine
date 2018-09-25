package com.main;

import java.util.Map;
import java.util.Scanner;

import com.keyoperations.TrieIndex;

public class SearchWord {
	static int counter = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			Scanner s = new Scanner(System.in);
			System.out.println("-------------------------------------------");

			System.out.println("Enter a word you want to search: ");
			System.out.println("or type 'ExitSystem' to end the searching");
			System.out.println("-------------------------------------------");

			String word = s.nextLine();
			
			while(!word.equalsIgnoreCase("exitsystem")) {
				SearchWord.search(word);
				System.out.println("-------------------------------------------");

				System.out.print("Enter the next word to search: ");
				word = s.nextLine();
				System.out.println("-------------------------------------------");

			}
			
			if(word.equalsIgnoreCase("exitsystem")) {
				System.out.println("Exiting....");
				Thread.sleep(3000);
				System.exit(0);
			}
			s.close();			
		}
		catch(Exception e) {
			
		}*/
	}
	public static void search(String word) {
		try {
			TrieIndex<Map<String,Integer>> search = new TrieIndex<>();
			search.search(word);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
