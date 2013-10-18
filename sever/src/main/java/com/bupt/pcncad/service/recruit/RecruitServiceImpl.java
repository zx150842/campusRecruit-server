package com.bupt.pcncad.service.recruit;

import com.bupt.pcncad.dao.bbsreply.IBBSReplyDao;
import com.bupt.pcncad.dao.bbssection.IBBSSectionDao;
import com.bupt.pcncad.dao.bbstopic.IBBSTopicDao;
import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.recruit.IRecruitDao;
import com.bupt.pcncad.dao.source.IDajieSourceDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.*;
import com.bupt.pcncad.domain.source.DajieSource;
import com.bupt.pcncad.service.util.IRecruitUtil;
import com.bupt.pcncad.util.IgnoreFieldProcessorImpl;
import com.bupt.pcncad.util.ParseProvince;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 下午7:43
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RecruitServiceImpl implements IRecruitService {
    @Autowired
    private IRecruitDao recruitDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IBBSReplyDao replyDao;
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private IBBSTopicDao topicDao;
    @Autowired
    private IBBSSectionDao sectionDao;
    @Autowired
    private IRecruitUtil recruitUtil;
    @Autowired
    private IDajieSourceDao dajieSourceDao;

    private int PAGE_SIZE = 20;

    //0 全部 1 名企
    public List<JSONObject> getRecruitList(String userId, int pageIndex,int order,int famous,
                                           String province, String industry, String type, String source) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        List<Job> jobs = null;
        if(user != null){

            jobs = this.recruitUtil.getRecruit(userId,pageIndex,order,famous, province, industry, type, source);

            List jobList = new ArrayList();
            Iterator it = jobs.iterator();
            while(it.hasNext()){
                JsonConfig config = new JsonConfig();
                Job job = ( Job) it.next();
                //Job job = (Job)oj[0];
                config.setJsonPropertyFilter(new IgnoreFieldProcessorImpl(true, new String[]{"inputTime","description",
                        "industry","company"}));
                JSONObject fromObject = this.recruitUtil.parse(job);
                /*JSONObject fromObject = JSONObject.fromObject(oj,config);
                jobList.add(new StringBuilder().append("createdTime:").append(oj.getCreatedTime().getTime()).toString())*/;
                jobList.add(fromObject);
            }
            return jobList;
        }
        return null;
    }

    public JSONObject getRecruitDetail(int recruitId) throws Exception{
        Job job  = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        if(job == null)
            return null;
        job.setClicks(job.getClicks()+1);
        this.recruitDao.update(job);
        Map map = new HashMap();
        if(job.getDescription() == null)
            map.put("description","");
        else
            map.put("description",job.getDescription());
        if(job.getContact() == null)
            map.put("contact","");
        else
            map.put("contact",job.getContact());
        if(job.getCompany().getState() == null)
            map.put("state","");
        else
            map.put("state",job.getCompany().getState());
        if(job.getCompany().getStatetime() == null)
            map.put("stateTime","");
        else
            map.put("stateTime",job.getCompany().getStatetime());
        map.put("sourceFrom",job.getSourceFrom());
        map.put("url",job.getUrl());
        if(job.getForm() == null)
            map.put("form","");
        else
        map.put("form",job.getForm());

        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }
    //  1 大街 2 北邮人 3 水木
    public boolean setRecruit(int sourceId, int companyId, String position, String description, String place, String contact,
                            String createdTime, int db, String url,String form,String img) throws Exception{
        final int DAJIE = 1;
        final int BYR = 2;
        final int SHUIMU = 3;
        String sourceFrom = null;
        DajieSource source = this.dajieSourceDao.findEntity("from DajieSource s where s.pid=?",sourceId);
        switch (db){
            case DAJIE:
                sourceFrom = "大街网";
                break;
            case BYR:
                sourceFrom = "北邮人";
                break;
            case SHUIMU:
                sourceFrom= "水木";
                break;
            default:
        }
        if(companyId == 0)
            return false;
        Company company = this.companyDao.findEntity("from Company com where com.id=?", companyId);
        String companyName = company.getCompanyName();
        Job job = new Job();
        job.setCompany(company);
        job.setCompanyName(companyName);
        job.setContact(contact);
        if(place != null)
            job.setPlace(place);
        else
            job.setPlace(ParseProvince.parse("全国"));
        job.setInputTime(new Date());
        job.setDescription(description);
        job.setPosition(position);
        job.setCreatedTime(createdTime);
        job.setSourceFrom(db+"");
        job.setUrl(url);
        if(form.equals("1"))
            job.setForm(source.getForm());
        job.setImgUrl(img);

//        job.setIndustry(company.getIndustry());
//        job.setType(company.getType());

        BBSTopic topic = new BBSTopic();
        BBSSection section = this.sectionDao.findEntity("from BBSSection s where s.companyID=?",companyId);
        topic.setTitle(position);
        if(description != null)
            topic.setBody(description);
        else
            topic.setBody(contact);
        topic.setCreatedTime(new Date());
        topic.setInputTime(new Date());
        topic.setBbsSection(section);
        topic.setUserName("系统");
        this.topicDao.save(topic);
        section.setTopics(section.getTopics()+1);
        this.sectionDao.update(section);
        job.setTopicID(topic.getId());
        this.recruitDao.save(job);
        return true;
    }

    public void joinRecruit(int recruitId, int join, String userId) throws Exception{
        Job job = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        Company company = job.getCompany();
        BBSSection bbsSection = this.sectionDao.findEntity("from BBSSection s where s.id=?",company.getSectionID());
        int num;
        int sectionNum;
        if(join == 1) {
            num = job.getJoins() + 1;
            sectionNum = bbsSection.getJoins() + 1;
        }

        else {
            num = job.getJoins() - 1;
            sectionNum = bbsSection.getJoins() - 1;
            if( num < 0){
                num = 0;
                sectionNum = 0;
            }
        }
        job.setClicks(job.getClicks()+1);
        job.setJoins(num);
        this.recruitDao.save(job);
        bbsSection.setJoins(sectionNum);
        this.sectionDao.update(bbsSection);

        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        Iterator it = user.getBbsSectionSet().iterator();
        Set<BBSSection> bbsSectionSet = new HashSet<BBSSection>();
        if(join == 1){
            while(it.hasNext()){
                bbsSectionSet.add((BBSSection)it.next());
            }
            bbsSectionSet.add(bbsSection);
        }
        else if(join == 0){
            while(it.hasNext()){
                BBSSection bs = (BBSSection)it.next();
                if(bs.getId() != company.getSectionID())
                    bbsSectionSet.add(bs);
            }
        }
        user.setBbsSectionSet(bbsSectionSet);
        this.userDao.update(user);
    }

    public String getDesc(int recruitId) throws Exception{
        Job job = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        job.setClicks(job.getClicks()+1);
        this.recruitDao.update(job);
        return job.getDescription();
    }

    public JSONObject getContact(int recruitId) throws Exception{
        Job job = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        Map map = new HashMap();
        if(job.getState() == null)
            map.put("state","");
        else
            map.put("state", job.getState());
        if(job.getStatetime() == null)
            map.put("stateTime", "");
        else
            map.put("stateTime", job.getStatetime());
        if(job.getContact() == null)
            map.put("contact", "");
        else
            map.put("contact", job.getContact());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }

    public String getForm(int recruitId) throws Exception{
        Job job = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        return job.getForm();
    }

    public String getImg(int recruitId) throws Exception{
        Job job = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        return job.getImgUrl();
    }

    public JSONObject getSimpleDetail(int recruitId) throws Exception{
        Job job = this.recruitDao.findEntity("from Job job where job.id=?",recruitId);
        Map map = new HashMap();
        map.put("content", job.getDescription());
        map.put("url",job.getUrl());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject;
    }
}
