package org.example;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) throws Exception {
        HashMap<Integer, HashMap<String, String>> processed_docs = Parser.parse_docs();
        HashMap<Integer, String> processed_qrys = Parser.parse_qrys();
        Indexer.get_index(processed_docs);
        Searcher.search(processed_qrys);
    }

}