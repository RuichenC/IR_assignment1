package lucene;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

    public static void search(HashMap<Integer, String> queries) throws Exception {

        String index = "src/index";
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new BM25Similarity());
        Analyzer analyzer = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        HashMap<String, Float> score_booster = new HashMap<String, Float>();
        score_booster.put("Title", 0.65f);
        score_booster.put("Author", 0.04f);
        score_booster.put("Bibliography", 0.02f);
        score_booster.put("Text", 0.35f);
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                new String[] {"Title", "Author", "Bibliography", "Text"},
                analyzer, score_booster);

        File file = new File("src/cranfield/cran.results");
        PrintWriter writer = new PrintWriter(file, "UTF-8");

        for (Map.Entry<Integer, String> q : queries.entrySet()) {
            String qry = q.getValue();
            Query query = queryParser.parse(QueryParser.escape(qry));

            TopDocs topDocs = searcher.search(query, 1000);
            ScoreDoc[] hits = topDocs.scoreDocs;
//            formatted results file: query-id Q0 document-id rank score STANDARD
            for(ScoreDoc sd:hits)
            {
                Document docc = searcher.doc(sd.doc);
//                the doc id
                int s = Integer.parseInt(docc.get("Id"));
                writer.println((q.getKey()) + " Q0 " + s + " 0 " + sd.score + " EXP");
//	        	System.out.println((q.getKey()) + " Q0 " + s + " 0 " + sd.score + " EXP");
            }

        }
        writer.close();
        reader.close();
    }
}
