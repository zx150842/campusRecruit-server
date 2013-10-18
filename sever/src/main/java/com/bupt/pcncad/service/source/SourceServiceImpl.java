package com.bupt.pcncad.service.source;

import com.bupt.pcncad.dao.careerTalk.ICareerTalkDao;
import com.bupt.pcncad.dao.ctsource.ICTSourceDao;
import com.bupt.pcncad.dao.source.*;
import com.bupt.pcncad.domain.CareerTalk;
import com.bupt.pcncad.domain.source.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-4
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SourceServiceImpl implements ISourceService {

    @Autowired
    private IDajieSourceDao dajieSourceDao;
    @Autowired
    private IBYRSourceDao byrSourceDao;
    @Autowired
    private IShuiMuDao shuiMuDao;
    @Autowired
    private ICTSourceDao ctSourceDao;
    @Autowired
    private IBYRCTSourceDao byrctSourceDao;
    @Autowired
    private IHTCTSourceDao htctSourceDao;
    @Autowired
    private ICareerTalkDao careerTalkDao;

    //校园招聘     1 大街 2 北邮人 3 水木
    public JSONObject getSource() throws Exception{
        DajieSource dajieSource = this.dajieSourceDao.findEntity("from DajieSource s where s.insertFlag=0 and s.releaseDate>?","09.26");
        if(dajieSource != null){
            dajieSource.setInsertFlag(2);
            this.dajieSourceDao.update(dajieSource);
        }

        BYRSource byrSource = null;
        ShuiMuBBSSource shuiMuBBSSource = null;
        Map map = new HashMap();
        JSONObject fromObject = null;
        if(dajieSource == null) {
            byrSource = this.byrSourceDao.findEntity("from BYRSource s where s.insertFlag=0 and s.releaseDate>?","2013-09-28 00:00:00");
            if(byrSource != null){
                byrSource.setInsertFlag(2);
                this.byrSourceDao.update(byrSource);
            }

            if(byrSource == null){
                shuiMuBBSSource = this.shuiMuDao.findEntity("from ShuiMuBBSSource s where s.insertFlag=0 and s.releaseDate>?","2013-09-28 00:00:00");
                if(shuiMuBBSSource != null){
                    shuiMuBBSSource.setInsertFlag(2);
                    this.shuiMuDao.update(shuiMuBBSSource);
                }

                if(shuiMuBBSSource == null){
                    map.put("pid","");
                    map.put("title","");
                    map.put("releaseDate","");
                    map.put("jobdetails","");
                    map.put("url","");
                    map.put("db",3);
                    map.put("industry","");
                    map.put("type","");
                    map.put("province","");
                }
                else {                                  //录入水木信息
                    map.put("pid",shuiMuBBSSource.getPid());
                    map.put("title",shuiMuBBSSource.getTitle());
                    map.put("releaseDate",shuiMuBBSSource.getReleaseDate());
                    map.put("jobdetails",shuiMuBBSSource.getJobdetails());
                    map.put("url",shuiMuBBSSource.getUrl());
                    map.put("db",3);
                    map.put("industry",shuiMuBBSSource.getIndustry());
                    map.put("type",shuiMuBBSSource.getType());
                    map.put("province",shuiMuBBSSource.getProvince());
                }
            }
            else{                                 //录入byr信息
                map.put("pid",byrSource.getPid());
                map.put("title",byrSource.getTitle());
                map.put("releaseDate",byrSource.getReleaseDate());
                map.put("jobdetails",byrSource.getJobdetails());
                map.put("url",byrSource.getUrl());
                map.put("db",2);
                map.put("industry",byrSource.getIndustry());
                map.put("type",byrSource.getType());
                map.put("province",byrSource.getProvince());
            }
            map.put("category","");
            map.put("state","");
            map.put("statetime","");
            map.put("isfame","");
            map.put("form",0);
            map.put("img","");
        }
        else{
            map.put("pid",dajieSource.getPid());
            map.put("title",dajieSource.getTitle());
            map.put("releaseDate",dajieSource.getReleaseDate());
            map.put("jobdetails",dajieSource.getJobdetails());
            map.put("city",dajieSource.getCity());
            map.put("type",dajieSource.getType());
            map.put("category",dajieSource.getCategory());
            map.put("industry",dajieSource.getIndustry());
            map.put("url",dajieSource.getUrl());
            if(dajieSource.getState() == null)
                map.put("state","");
            else
                map.put("state",dajieSource.getState());
            if(dajieSource.getStatetime() == null)
                map.put("statetime","");
            else
                map.put("statetime",dajieSource.getStatetime());
            map.put("isfame",dajieSource.getIsfame());
            map.put("db",1);
            if(dajieSource.getForm() == null){
                map.put("form","");
                map.put("have_form",0);
            }
            else {
                map.put("form",dajieSource.getForm());
                map.put("have_form",1);
            }
            if(dajieSource.getImageurl() == null)
                map.put("img","");
            else
                map.put("img",dajieSource.getImageurl());
            map.put("industry","");
            map.put("type","");
            map.put("province","");
        }
        fromObject = JSONObject.fromObject(map);

        return fromObject;
    }

    public void setSource(int sourceId,int db) throws Exception{
        switch (db){
            case 1:
                DajieSource dajieSource = this.dajieSourceDao.findEntity("from DajieSource s where s.id=?", sourceId);
                dajieSource.setInsertFlag(1);
                this.dajieSourceDao.update(dajieSource);
                List<DajieSource> dajieSources = this.dajieSourceDao.find("from DajieSource s where s.insertFlag=2");
                for(int i=0; i<dajieSources.size(); i++){
                    DajieSource dajieSource1 = dajieSources.get(i);
                    dajieSource1.setInsertFlag(0);
                    this.dajieSourceDao.update(dajieSource1);
                }
                break;
            case 2:
                BYRSource byrSource = this.byrSourceDao.findEntity("from BYRSource s where s.id=?", sourceId);
                byrSource.setInsertFlag(1);
                this.byrSourceDao.update(byrSource);
                List<BYRSource> byrSources = this.byrSourceDao.find("from BYRSource s where s.insertFlag=2");
                for(int i=0; i<byrSources.size(); i++){
                    BYRSource byrSource1 = byrSources.get(i);
                    byrSource1.setInsertFlag(0);
                    this.byrSourceDao.update(byrSource1);
                }
                break;
            case 3:
                ShuiMuBBSSource shuiMuBBSSource = this.shuiMuDao.findEntity("from ShuiMuBBSSource s where s.id=?",sourceId);
                shuiMuBBSSource.setInsertFlag(1);
                this.shuiMuDao.update(shuiMuBBSSource);
                List<ShuiMuBBSSource> shuiMuBBSSources = this.shuiMuDao.find("from ShuiMuBBSSource s where s.insertFlag=2");
                for(int i=0; i<shuiMuBBSSources.size(); i++){
                    ShuiMuBBSSource shuiMuBBSSource1 = shuiMuBBSSources.get(i);
                    shuiMuBBSSource1.setInsertFlag(0);
                    this.shuiMuDao.update(shuiMuBBSSource1);
                }
                break;
            default:
                break;
        }

    }

    //宣讲会
    public JSONObject getCTSource() throws Exception{      //大街1 北邮就业信息网2 海投3
        List<CareerTalk> careerTalkList = this.careerTalkDao.find("from CareerTalk c where c.deleteFlag=2");
        for(int i=0; i<careerTalkList.size(); i++){
            int id = careerTalkList.get(i).getOrgId();
            int db = careerTalkList.get(i).getDb();
            switch (db){
                case 1:
                    CTSource ctSource = this.ctSourceDao.findEntity("from CTSource ct where ct.id=?",id);
                    ctSource.setInsertFlag(0);
                    this.ctSourceDao.update(ctSource);
                    break;
                case 2:
                    BYRCTSource byrctSource = this.byrctSourceDao.findEntity("from BYRCTSource ct where ct.id=?",id);
                    byrctSource.setInsertFlag(0);
                    this.byrctSourceDao.update(byrctSource);
                    break;
                case 3:
                    HTCTSource htctSource = this.htctSourceDao.findEntity("from HTCTSource ct where ct.id=?",id);
                    htctSource.setInsertFlag(0);
                    this.htctSourceDao.update(htctSource);
                    break;
            }
        }
        CTSource ctSource = this.ctSourceDao.findEntity("from CTSource ct where ct.insertFlag=0");
        Map map = new HashMap();
        if(ctSource == null){
            BYRCTSource byrctSource = this.byrctSourceDao.findEntity("from BYRCTSource ct where ct.insertFlag=0");
            if(byrctSource == null) {
                HTCTSource htctSource = this.htctSourceDao.findEntity("from HTCTSource ct where ct.insertFlag=0");
                map.put("date",htctSource.getDate());
                map.put("local",htctSource.getLocal());
                map.put("school",htctSource.getSchool());
                map.put("time",htctSource.getTime());
                map.put("title",htctSource.getTitle());
                map.put("url","");
                map.put("db",3);
                map.put("id",htctSource.getId());
            }
            else {
                map.put("date",byrctSource.getDate());
                map.put("local",byrctSource.getLocal());
                map.put("school","北京邮电大学");
                map.put("time",byrctSource.getTime());
                map.put("title",byrctSource.getTitle());
                map.put("url","");
                map.put("db",2);
                map.put("id",byrctSource.getId());
            }
        }
        else {
            map.put("date",ctSource.getDate());
            map.put("local",ctSource.getLocal());
            map.put("school",ctSource.getSchool());
            map.put("time",ctSource.getTime());
            map.put("title",ctSource.getTitle());
            map.put("url",ctSource.getUrl());
            map.put("db",1);
            map.put("id",ctSource.getId());
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }

    public void setCTSource(int ctSourceId, int db) throws Exception{
        switch (db){
            case 1:
                CTSource ctSource = this.ctSourceDao.findEntity("from CTSource ct where ct.id=?",ctSourceId);
                ctSource.setInsertFlag(1);
                this.ctSourceDao.update(ctSource);
                List<CTSource> ctSources = this.ctSourceDao.find("from CTSource ct where ct.insertFlag=2");
                for(int i=0; i<ctSources.size(); i++){
                    CTSource ctSource1 = ctSources.get(i);
                    ctSource1.setInsertFlag(0);
                    this.ctSourceDao.update(ctSource1);
                }
                break;
            case 2:
                BYRCTSource byrctSource = this.byrctSourceDao.findEntity("from BYRCTSource ct where ct.id=?",ctSourceId);
                byrctSource.setInsertFlag(1);
                this.byrctSourceDao.update(byrctSource);
                List<BYRCTSource> byrctSources = this.byrctSourceDao.find("from BYRCTSource ct where ct.insertFlag=2");
                for(int i=0; i<byrctSources.size(); i++){
                    BYRCTSource byrctSource1 = byrctSources.get(i);
                    byrctSource1.setInsertFlag(0);
                    this.byrctSourceDao.update(byrctSource1);
                }
                break;
            case 3:
                HTCTSource htctSource = this.htctSourceDao.findEntity("from HTCTSource ct where ct.id=?", ctSourceId);
                htctSource.setInsertFlag(1);
                this.htctSourceDao.update(htctSource);
                List<HTCTSource> htctSources = this.htctSourceDao.find("from HTCTSource ct where ct.insertFlag=2");
                for(int i=0; i<htctSources.size(); i++){
                    HTCTSource htctSource1 = htctSources.get(i);
                    htctSource1.setInsertFlag(0);
                    this.htctSourceDao.update(htctSource1);
                }
                break;
        }

    }
}
