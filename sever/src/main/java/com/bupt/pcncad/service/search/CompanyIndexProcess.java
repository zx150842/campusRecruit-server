//package com.bupt.pcncad.service.search;
//
//import com.bupt.pcncad.dao.company.ICompanyDao;
//import com.bupt.pcncad.domain.Company;
//import jeasy.analysis.MMAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.index.IndexWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * User: naruto
// * Date: 13-3-3
// * Time: 下午5:38
// * To change this template use File | Settings | File Templates.
// */
//@Service
//@Qualifier("CompanyIndexProcess")
//public class CompanyIndexProcess extends AbstractIndexProcess {
//    @Autowired
//    private ICompanyDao companyDao;
//
//    @Override
//    public void createIndex() throws Exception {
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
//        IndexWriter iw = new IndexWriter("D:/sever/data/lucene/index/company", new MMAnalyzer(),true);
//        iw.setUseCompoundFile(false);
//        List<Company> list = companyDao.findAll();
//        for (Company company : list) {
//            Document document = new Document();
//            Field name = new Field("name", company.getCompanyName(), Field.Store.YES, Field.Index.TOKENIZED);
////            Field size = new Field("creator", company.getAdmin().getUserName(), Field.Store.YES, Field.Index.UN_TOKENIZED);
////            Field uploader = new Field("course", company.getCourse().getName(), Field.Store.YES, Field.Index.UN_TOKENIZED);
//            Field id= new Field("id", company.getId()+"", Field.Store.YES, Field.Index.NO);
//            document.add(name);
////            document.add(size);
////            document.add(uploader);
//            document.add(id);
//            iw.addDocument(document);
//        }
//        iw.close();
//    }
//}
