//package com.bupt.pcncad.service.search;
//
//import jeasy.analysis.MMAnalyzer;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.index.TermDocs;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.Hits;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// * User: naruto
// * Date: 13-3-3
// * Time: 下午5:58
// * To change this template use File | Settings | File Templates.
// */
//public abstract class AbstractIndexProcess {
//    private static final String RESOURCE_INDEX_PATH = "data" + File.separator + "lucene" + File.separator + "index" + File.separator + "resource";
//    private static final String COMPANY_INDEX_PATH = "data" + File.separator + "lucene" + File.separator + "index" + File.separator + "company";
//    private static final Map<Class, String> pathMap = new HashMap<Class, String>();
//
//    static {
//        pathMap.put(CompanyIndexProcess.class, COMPANY_INDEX_PATH);
////        pathMap.put(CommunityIndexProcess.class, COMMUNITY_INDEX_PATH);
//    }
//
//    public abstract void createIndex() throws Exception;
//
//    public TermDocs searchTermDocs(String keyType, String keyWord) throws Exception {
//        IndexSearcher is = new IndexSearcher("D:/sever/data/lucene/index/company");
//        Term term = new Term(keyType, keyWord);
//        return is.getIndexReader().termDocs(term);
//
//    }
//
//
//    public Hits searchHits(String keyType, String keyWord) throws Exception {
//        System.out.println("D:/sever/data/lucene/index/company");
////        IndexSearcher is = new IndexSearcher(pathMap.get(this.getClass()));
//        IndexSearcher is = new IndexSearcher("D:/sever/data/lucene/index/company");
//
//        String key = keyWord;
//        MMAnalyzer analyzer = new MMAnalyzer();
//        try{
//            System.out.println(analyzer.segment(keyWord, " "));
//            key = analyzer.segment(keyWord, " ");
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//
//
//        QueryParser parser = new QueryParser(keyType, new MMAnalyzer());
//        parser.setDefaultOperator(QueryParser.OR_OPERATOR);
//        Query query = parser.parse(key);
////        Term term = new Term(keyType,keyWord);
////        Query query = new TermQuery(term);
//        return is.search(query);
//    }
//
//    protected static String getPathByClass(Class clazz) {
//        return pathMap.get(clazz);
//    }
//
//}
