package com.keyoperations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class TrieIndex<Value> {
	
	static TrieIndex<Map<String,Integer>> map = new TrieIndex<>();
	private int N;       // size
    private Node root;   // root of TST

    private class Node {
        private char c;                 // character
        private Node left, mid, right;  // left, middle, and right subtries
        private Value map;              // value associated with string
    }

    // return number of key-value pairs
    public int size() {
        return N;
    }

   /**************************************************************
    * Is string key in the symbol table?
    **************************************************************/
    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.map;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }


   /**************************************************************
    * Insert string s into the symbol table.
    **************************************************************/
    public void put(String s, Value val) {
        if (!contains(s)) N++;
        root = put(root, s, val, 0);
    }

    private Node put(Node x, String s, Value val, int d) {
        char c = s.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)             x.left  = put(x.left,  s, val, d);
        else if (c > x.c)             x.right = put(x.right, s, val, d);
        else if (d < s.length() - 1)  x.mid   = put(x.mid,   s, val, d+1);
        else                          x.map   = val;
        return x;
    }

    
    public void search(String word) {
    	SortMap sort = new SortMap();
    	List<Map.Entry<String,Integer>> list = null;
		try {		
			Scanner scan=new Scanner(System.in);
			long start=0,end = 0;
			double total=0.0;
			start = System.currentTimeMillis();
			Map<String,Integer> m = map.get(word);
			if(m == null) {		
					StringMatchFile fileOperations = new StringMatchFile(word);
					Map<String,Integer> mFile = fileOperations.search();
					if(mFile.isEmpty()) {
						end = System.currentTimeMillis();

						System.out.println("-------------------------------------------");

						System.out.println("ERROR::Word does not exist!!");	
						System.out.println("-------------------------------------------");

					}
					
					else {
						
						list = new LinkedList<Map.Entry<String,Integer>>(mFile.entrySet());
						map.put(word, mFile);
						sort.sort(list, 0, list.size()-1);
						end = System.currentTimeMillis();
						System.out.println("-------------------------------------------");
						int i=list.size()-1;
						int count=0;
						String chc="n";
						for(;i>=0;i--) {
							if((list.get(i).getValue()!=0)&& (count<10) && chc.equals("n")) {
								System.out.println("File:\t"+list.get(i).getKey().substring(list.get(i).getKey().lastIndexOf('/')+1).replace(".txt", "")+"----------->Occurance:"+list.get(i).getValue());
								count++;
								
							}
							else if((count>=10) && chc.equals("n")){
								System.out.println("Enter 'n' to view next...");
								String a=scan.nextLine();
								if(a.equalsIgnoreCase("n")) {
									count=0;
									chc="n";
									System.out.println("---NEXT------------------------------------");

								}
								else {
									chc="s";
								}
							}
						}
						System.out.println("-------------------------------------------");

						
						
					}			
			}
			else {					
				list = new LinkedList<Map.Entry<String,Integer>>(m.entrySet());
				sort.sort(list, 0, list.size()-1);
				end = System.currentTimeMillis();
				System.out.println("-------------------------------------------");
				int i=list.size()-1;
				int count=0;
				String chc="n";
				for(;i>=0;i--) {
					if((list.get(i).getValue()!=0)&& (count<10) && chc.equals("n")) {
						System.out.println("File:\t"+list.get(i).getKey().substring(list.get(i).getKey().lastIndexOf('/')+1).replace(".txt", "")+"----------->Occurance:"+list.get(i).getValue());
						count++;
					}
					else if((count>=10) && chc.equals("n")){
						System.out.println("Enter 'n' to view next...");
						String a=scan.nextLine();
						if(a.equalsIgnoreCase("n")) {
							count=0;
							chc="n";
							System.out.println("---NEXT------------------------------------");

						}
						else {
							chc="s";
						}
					}
				}
				System.out.println("-------------------------------------------");

			}
			total = (double)end-start;
			System.out.printf("Total time taken = %.1fms\n",total);
		}
		catch(Exception e) {
			throw e;
		}
    }
    
    
    public void searchCustom(String word,String fileName) throws Exception {
    	SortMap sort = new SortMap();
    	List<Map.Entry<String,Integer>> list = null;
		try {			
			long start=0,end = 0;
			double total=0.0;
			start = System.currentTimeMillis();
			Map<String,Integer> m = map.get(word);
			if(m == null) {		
					StringMatchFile fileOperations = new StringMatchFile(word);
					Map<String,Integer> mFile = fileOperations.searchCustom(word, fileName);
					if(mFile.isEmpty()) {	
						end = System.currentTimeMillis();
						System.out.println("-------------------------------------------");

						System.out.println("ERROR::Word does not exist!!");	
						System.out.println("-------------------------------------------");

					}
					
					else {
						list = new LinkedList<Map.Entry<String,Integer>>(mFile.entrySet());
						map.put(word, mFile);
						sort.sort(list, 0, list.size()-1);
						end = System.currentTimeMillis();
						System.out.println("-------------------------------------------");

						for(int i=list.size()-1;i>=0;i--) {
							if(list.get(i).getValue()!=0)
								System.out.println("Title:\t"+list.get(i).getKey().substring(list.get(i).getKey().lastIndexOf('/')+1).replace(".txt", "")+"----------->Occurance:"+list.get(i).getValue());
						}
						System.out.println("-------------------------------------------");

						
						
					}			
			}
			else {					
				list = new LinkedList<Map.Entry<String,Integer>>(m.entrySet());
				sort.sort(list, 0, list.size()-1);
				end = System.currentTimeMillis();
				System.out.println("-------------------------------------------");

				for(int i=list.size()-1;i>=0;i--) {
					if(list.get(i).getValue()!=0)
						System.out.println("Title:\t"+list.get(i).getKey().substring(list.get(i).getKey().lastIndexOf('/')+1).replace(".txt", "")+"----------->Occurance:"+list.get(i).getValue());
				}
				System.out.println("-------------------------------------------");

			}
			total = (double)end-start;
			System.out.printf("Total time taken = %.1fms\n",total);
		}
		catch(Exception e) {
			throw e;
		}
    }
    
}
