package lucene;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) throws Exception {
//        get everything parsed and indexed then search
        HashMap<Integer, HashMap<String, String>> doc = Parser.parse_docs();
        HashMap<Integer, String> qry = Parser.parse_qrys();
        Indexer.get_index(doc);
        Searcher.search(qry);
    }

}