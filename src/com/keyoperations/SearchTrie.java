package com.keyoperations;

import java.util.Map;

public class SearchTrie {
	public static void search(String word) {
		TrieIndex<Map<String,Integer>> st = new TrieIndex<>();
		st.get(word);
	}
}
