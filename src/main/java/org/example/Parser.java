package org.example;

import java.io.BufferedReader;
import java.util.HashMap;
import java.io.FileReader;

public class Parser {
    public static HashMap<Integer, HashMap<String, String>> parse_docs() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("src/cranfield/cran.all.1400"));
        String line=null, property=null;
        int count=0;
        HashMap<Integer, HashMap<String, String>> processed_docs = new HashMap<Integer, HashMap<String, String>>();
        while((line = br.readLine())!=null) {
            String append_line;
            if(line.startsWith(".I")) {
                count++;
                property=".I";
                processed_docs.put(count, new HashMap<String, String>());
            }
            else if(line.startsWith(".A"))
                property=".A";
            else if(line.startsWith(".T"))
                property=".T";
            else if(line.startsWith(".B"))
                property=".B";
            else if(line.startsWith(".W"))
                property=".W";
            else {
                if(property==".A") {
                    if(processed_docs.get(count).get("Author")!=null) {
                        append_line = processed_docs.get(count).get("Author");
                        processed_docs.get(count).put("Author", append_line+" "+line);
                    }
                    else
                        processed_docs.get(count).put("Author", line);
                }
                else if(property==".T") {
                    if(processed_docs.get(count).get("Title")!=null) {
                        append_line = processed_docs.get(count).get("Title");
                        processed_docs.get(count).put("Title", append_line+" "+line);
                    }
                    else
                        processed_docs.get(count).put("Title", line);
                }
                else if(property==".B") {
                    if(processed_docs.get(count).get("Bibliography")!=null) {
                        append_line = processed_docs.get(count).get("Bibliography");
                        processed_docs.get(count).put("Bibliography", append_line+" "+line);
                    }
                    else
                        processed_docs.get(count).put("Bibliography", line);
                }
                else if(property==".W") {
                    if(processed_docs.get(count).get("Text")!=null) {
                        append_line = processed_docs.get(count).get("Text");
                        processed_docs.get(count).put("Text", append_line+" "+line);
                    }
                    else
                        processed_docs.get(count).put("Text", line);
                }
            }
        }

        br.close();
        return processed_docs;
    }
    public static HashMap<Integer, String> parse_qrys() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("src/cranfield/cran.qry"));
        String line=null, property=null;
        int count=0;
        HashMap<Integer, String> processed_qry = new HashMap<Integer, String>();
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
                    if(processed_qry.get(count)!=null) {
                        append_line = processed_qry.get(count);
                        processed_qry.put(count, append_line+" "+line);
                    }
                    else
                        processed_qry.put(count, line);
                }
            }
        }
        br.close();
        System.out.println("Files parsed successfully");
        return processed_qry;
    }

}
