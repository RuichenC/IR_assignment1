package lucene;

import java.io.BufferedReader;
import java.util.HashMap;
import java.io.FileReader;

public class Parser {
//    get documents parsed
    public static HashMap<Integer, HashMap<String, String>> parse_docs() throws Exception{
        String line;
        String property = null;
        int count=0;
        BufferedReader br = new BufferedReader(new FileReader("src/cranfield/cran.all.1400"));

        HashMap<Integer, HashMap<String, String>> docs = new HashMap<Integer, HashMap<String, String>>();
        while((line = br.readLine())!=null) {
            String append_line;
//            id
            if(line.startsWith(".I")) {
                count++;
                property=".I";
                docs.put(count, new HashMap<String, String>());
            }
//            author
            else if(line.startsWith(".A"))
                property=".A";
//            title
            else if(line.startsWith(".T"))
                property=".T";
//            bib
            else if(line.startsWith(".B"))
                property=".B";
//            textual document
            else if(line.startsWith(".W"))
                property=".W";
            else {
                if(property==".A") {
                    if(docs.get(count).get("Author")!=null) {
                        append_line = docs.get(count).get("Author");
                        docs.get(count).put("Author", append_line+" "+line);
                    }
                    else
                        docs.get(count).put("Author", line);
                }
                else if(property==".T") {
                    if(docs.get(count).get("Title")!=null) {
                        append_line = docs.get(count).get("Title");
                        docs.get(count).put("Title", append_line+" "+line);
                    }
                    else
                        docs.get(count).put("Title", line);
                }
                else if(property==".B") {
                    if(docs.get(count).get("Bibliography")!=null) {
                        append_line = docs.get(count).get("Bibliography");
                        docs.get(count).put("Bibliography", append_line+" "+line);
                    }
                    else
                        docs.get(count).put("Bibliography", line);
                }
                else if(property==".W") {
                    if(docs.get(count).get("Text")!=null) {
                        append_line = docs.get(count).get("Text");
                        docs.get(count).put("Text", append_line+" "+line);
                    }
                    else
                        docs.get(count).put("Text", line);
                }
            }
        }

        br.close();
        System.out.println("Documents parsed");
        return docs;
    }
//    get queries parsed
    public static HashMap<Integer, String> parse_qrys() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("src/cranfield/cran.qry"));
        String line=null, property=null;
        int count=0;
        HashMap<Integer, String> qrys = new HashMap<Integer, String>();
        while((line = br.readLine())!=null) {
            String append_line=null;
            if(line.startsWith(".I")) {
                count++;
                property=".I";
            }
            else if(line.startsWith(".W"))
                property=".W";
            else {
                if(property==".W") {
                    if(qrys.get(count)!=null) {
                        append_line = qrys.get(count);
                        qrys.put(count, append_line+" "+line);
                    }
                    else
                        qrys.put(count, line);
                }
            }
        }
        br.close();
        System.out.println("Queries parsed");
        return qrys;
    }

}
