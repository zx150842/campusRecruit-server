package com.bupt.pcncad;

/**
 * Created with IntelliJ IDEA.
 * User: naruto
 * Date: 13-3-3
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */

import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.ctsource.ICTSourceDao;
import com.bupt.pcncad.dao.source.*;
import com.bupt.pcncad.domain.Company;
import com.bupt.pcncad.domain.source.*;
import com.bupt.pcncad.service.careerTalk.ICareerTalkService;
import com.bupt.pcncad.service.recruit.IRecruitService;
import com.bupt.pcncad.service.search.ICompanySearchService;
import com.bupt.pcncad.service.util.http.SearchCompanyInBaike;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class CreateIndexTest {
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private CompassTemplate compassTemplate;
    @Autowired
    private ICompanySearchService companySearchService;
    @Autowired
    private IDajieSourceDao dajieSourceDao;
    @Autowired
    private SearchCompanyInBaike searchCompanyInBaike;
    @Autowired
    private ICareerTalkService careerTalkService;
    @Autowired
    private IRecruitService recruitService;
    @Autowired
    private ICTSourceDao ctSourceDao;
    @Autowired
    private IBYRCTSourceDao byrctSourceDao;
    @Autowired
    private IHTCTSourceDao htctSourceDao;
    @Autowired
    private IBYRSourceDao byrSourceDao;
    @Autowired
    private IShuiMuDao shuiMuDao;

//    @Autowired
//    @Qualifier("CompanyIndexProcess")
//    private AbstractIndexProcess companyIndexProcess;
//
//    @Test
//    public void createIndex() throws Exception {
//        companyIndexProcess.createIndex();
//
//    }

    @Test
    public void createIndex() throws Exception{
        List<Company> companyList = this.companyDao.findAll();
        CompassSession session=compassTemplate.getCompass().openSession();
        for(int i=0; i<companyList.size(); i++){
//            session.save(companyList.get(i));
//            ExternalDBBean  externalDBBean = new ExternalDBBean();
//            externalDBBean.setBeanID(IDFactory.getId());
//            List<String> parmList = new ArrayList<String>();
//            for(int i=0;i<res.getMetaData().getColumnCount();i++)
//            {
//                parmList.add(res.getString(i+1));
//            }
//            externalDBBean.setPramList(parmList);
//            externalDBBean.setFileType("otherDB");
            /** *********索引对象**************** */
            compassTemplate.create(companyList.get(i));
        }

//        SingleCompassGps gps=(SingleCompassGps)context.getBean("compassGps");
//        gps.start();
//        gps.index();
//        gps.stop();
    }

    @Test
    public void test() throws Exception{
        this.companySearchService.test();
    }

    @Test
    public void autoSetRecruit() throws Exception{
        List<DajieSource> dajieSourceList = this.dajieSourceDao.find("from DajieSource s where s.insertFlag=0");
        for(int i=0; i<dajieSourceList.size(); i++){
            DajieSource dajieSource = dajieSourceList.get(i);
            int id = this.companySearchService.findResult(dajieSource.getTitle());
            if(id > 0){
                Company company = this.companyDao.findEntity("from Company com where com.id=?",id);
                System.out.println("------title:"+dajieSource.getTitle()+"-------result:"+company.getCompanyName());
                //String schoolName,String place,int companyId,String date,String time, String url,int db
                //ParseSchool.parse(school),place,companyId,ParseDate.parse(date,3),time,url,db
//                String schoolName = ParseSchool(dajieSource.get)
//                this.careerTalkService.setCareerTalk()
            }
            else {
                String key = this.companySearchService.divideWord(dajieSource.getTitle());
                System.out.println(dajieSource.getTitle());
                System.out.println(key);
                this.searchCompanyInBaike.searchCompany(key);
            }
        }

        List<BYRSource> byrSourceList = this.byrSourceDao.find("from BYRSource s where s.insertFlag=0");
        for(int i=0; i<byrSourceList.size(); i++){
            BYRSource byrSource = byrSourceList.get(i);
            int id = this.companySearchService.findResult(byrSource.getTitle());
            if(id > 0){
                Company company = this.companyDao.findEntity("from Company com where com.id=?",id);
                System.out.println("------title:"+byrSource.getTitle()+"-------result:"+company.getCompanyName());

//                if(db == 1)
//                    success = this.recruitService.setRecruit(sourceId,companyId, position, description, ParseProvince.parse(place), contact, ParseDate.parse(createdTime,2),db,url,form,img);
//                else
//                    success = this.recruitService.setRecruit(sourceId,companyId, position, description, ParseProvince.parse(place), contact, createdTime,db,url,form,img);
                int sourceId = byrSource.getPid();
                int companyId = id;
                String position = byrSource.getTitle();
                String description = byrSource.getJobdetails();
                String place = byrSource.getProvince();
                String contact = null;
                String createdTime = byrSource.getReleaseDate();
                int db = 2;
                String url = byrSource.getUrl();
                String form = null;
                String img = null;
//                this.recruitService.setRecruit(sourceId,companyId,position,description, ParseProvince.parse(place),contact,createdTime,db,url,form,img);
            }
            else {
                String key = this.companySearchService.divideWord(byrSource.getTitle());
                System.out.println(byrSource.getTitle());
                System.out.println(key);
                this.searchCompanyInBaike.searchCompany(key);
            }
        }

        List<ShuiMuBBSSource> shuiMuBBSSourceList = this.shuiMuDao.find("from ShuiMuBBSSource s where s.insertFlag=0");
        for(int i=0; i<shuiMuBBSSourceList.size(); i++){
            ShuiMuBBSSource shuiMuBBSSource = shuiMuBBSSourceList.get(i);
            int id = this.companySearchService.findResult(shuiMuBBSSource.getTitle());
            if(id > 0){
                Company company = this.companyDao.findEntity("from Company com where com.id=?",id);
                System.out.println("------title:"+shuiMuBBSSource.getTitle()+"-------result:"+company.getCompanyName());

//                if(db == 1)
//                    success = this.recruitService.setRecruit(sourceId,companyId, position, description, ParseProvince.parse(place), contact, ParseDate.parse(createdTime,2),db,url,form,img);
//                else
//                    success = this.recruitService.setRecruit(sourceId,companyId, position, description, ParseProvince.parse(place), contact, createdTime,db,url,form,img);
                int sourceId = shuiMuBBSSource.getPid();
                int companyId = id;
                String position = shuiMuBBSSource.getTitle();
                String description = shuiMuBBSSource.getJobdetails();
                String place = shuiMuBBSSource.getProvince();
                String contact = null;
                String createdTime = shuiMuBBSSource.getReleaseDate();
                int db = 3;
                String url = shuiMuBBSSource.getUrl();
                String form = null;
                String img = null;
//                this.recruitService.setRecruit(sourceId,companyId,position,description, ParseProvince.parse(place),contact,createdTime,db,url,form,img);
            }
            else {
                String key = this.companySearchService.divideWord(shuiMuBBSSource.getTitle());
                System.out.println(shuiMuBBSSource.getTitle());
                System.out.println(key);
                this.searchCompanyInBaike.searchCompany(key);
            }
        }
    }

    @Test
    public void autoSetCareerTalk() throws Exception{
        List<CTSource> ctSourceList = this.ctSourceDao.find("from CTSource ct where ct.insertFlag=0");
        for(int i=0; i<ctSourceList.size(); i++){
            CTSource ctSource = ctSourceList.get(i);
            int id = this.companySearchService.findResult(ctSource.getTitle());
            if(id > 0){
                Company company = this.companyDao.findEntity("from Company com where com.id=?",id);
                System.out.println("------title:"+ctSource.getTitle()+"-------result:"+company.getCompanyName());
//                String schoolName = ParseSchool.parse(ctSource.getSchool());
//                String place = ctSource.getLocal();
//                int companyId = id;
//                int db = 1;
//                String date = ParseDate.parse(ctSource.getDate(),db);
//                String time = ctSource.getTime();
//                String url = ctSource.getUrl();
//                this.careerTalkService.setCareerTalk(schoolName,place,companyId,date,time,url,db);
            }
            else {
                String key = this.companySearchService.divideWord(ctSource.getTitle());
                System.out.println(ctSource.getTitle());
                System.out.println(key);
                this.searchCompanyInBaike.searchCompany(key);
            }
        }
        List<BYRCTSource> byrctSourceList = this.byrctSourceDao.find("from BYRCTSource ct where ct.insertFlag=0");
        for(int i=0; i<byrctSourceList.size(); i++){
            BYRCTSource byrctSource = byrctSourceList.get(i);
            int id = this.companySearchService.findResult(byrctSource.getTitle());
            if(id > 0){
                Company company = this.companyDao.findEntity("from Company com where com.id=?",id);
                System.out.println("------title:"+byrctSource.getTitle()+"-------result:"+company.getCompanyName());
//                String schoolName = ParseSchool.parse(ctSource.getSchool());
//                String place = ctSource.getLocal();
//                int companyId = id;
//                int db = 1;
//                String date = ParseDate.parse(ctSource.getDate(),db);
//                String time = ctSource.getTime();
//                String url = ctSource.getUrl();
//                this.careerTalkService.setCareerTalk(schoolName,place,companyId,date,time,url,db);
            }
            else {
                String key = this.companySearchService.divideWord(byrctSource.getTitle());
                System.out.println(byrctSource.getTitle());
                System.out.println(key);
                this.searchCompanyInBaike.searchCompany(key);
            }
        }
        List<HTCTSource> htctSourceList = this.htctSourceDao.find("from HTCTSource ct where ct.insertFlag=0");
        for(int i=0; i<htctSourceList.size(); i++){
            HTCTSource htctSource = htctSourceList.get(i);
            int id = this.companySearchService.findResult(htctSource.getTitle());
            if(id > 0){
                Company company = this.companyDao.findEntity("from Company com where com.id=?",id);
                System.out.println("------title:"+htctSource.getTitle()+"-------result:"+company.getCompanyName());
//                String schoolName = ParseSchool.parse(ctSource.getSchool());
//                String place = ctSource.getLocal();
//                int companyId = id;
//                int db = 1;
//                String date = ParseDate.parse(ctSource.getDate(),db);
//                String time = ctSource.getTime();
//                String url = ctSource.getUrl();
//                this.careerTalkService.setCareerTalk(schoolName,place,companyId,date,time,url,db);
            }
            else {
                String key = this.companySearchService.divideWord(htctSource.getTitle());
                System.out.println(htctSource.getTitle());
                System.out.println(key);
                this.searchCompanyInBaike.searchCompany(key);
            }
        }

    }

    @Test
    public void findCompany() throws Exception{
        String key = "中铁上海工程局有限公司";
        this.searchCompanyInBaike.searchCompany(key);
    }
}
