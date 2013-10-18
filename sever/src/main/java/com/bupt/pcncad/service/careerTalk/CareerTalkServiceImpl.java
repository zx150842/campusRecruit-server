package com.bupt.pcncad.service.careerTalk;

import com.bupt.pcncad.dao.bbssection.IBBSSectionDao;
import com.bupt.pcncad.dao.bbstopic.IBBSTopicDao;
import com.bupt.pcncad.dao.careerTalk.ICareerTalkDao;
import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.*;
import com.bupt.pcncad.service.util.ICareerTalkUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-25
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CareerTalkServiceImpl implements ICareerTalkService {
    @Autowired
    private ICareerTalkDao careerTalkDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private IBBSTopicDao topicDao;
    @Autowired
    private IBBSSectionDao sectionDao;
    @Autowired
    private ICareerTalkUtil careerTalkUtil;

    // 0 全部 1 名企
    public List<JSONObject> getCareerTalkList(String userId, int pageIndex, int order, int famous,
                                              String province, String industry, String type, String school) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        if(user != null){
            String name = null;
            if(school == null)
                name = "全部";
            else
                name = new String(school.getBytes("ISO-8859-1"), "UTF-8");
            List<CareerTalk> careerTalkList = this.careerTalkUtil.getCareerTalk(userId,pageIndex,order,famous,province,industry,type,name);
            List careerTalks = new ArrayList();
            Iterator it = careerTalkList.iterator();
            while(it.hasNext()){
                CareerTalk careerTalk = (CareerTalk) it.next();
                JSONObject fromObject = this.careerTalkUtil.parse(careerTalk);
                careerTalks.add(fromObject);
            }
            return careerTalks;
        }
        return null;
    }

    public void setCareerTalkClicks(int careerTalkId) throws Exception{
        CareerTalk careerTalk = this.careerTalkDao.findEntity("from CareerTalk c where c.id=?",careerTalkId);
        if(careerTalk == null)
            return;
        careerTalk.setClicks(careerTalk.getClicks() + 1);
        this.careerTalkDao.update(careerTalk);
    }

    public CareerTalk getCareerTalkDetail(int careerTalkId) throws Exception{
        CareerTalk careerTalk = this.careerTalkDao.findEntity("from CareerTalk c where c.id=?",careerTalkId);
        if(careerTalk != null){
            careerTalk.setClicks(careerTalk.getClicks()+1);
            this.careerTalkDao.update(careerTalk);
        }
        return careerTalk;
    }

    public void joinCareerTalk(String userId, int careerTalkId, int join) throws Exception{
        CareerTalk careerTalk = this.careerTalkDao.findEntity("from CareerTalk c where c.id=?",careerTalkId);
        Company company = careerTalk.getCompany();
        BBSSection bbsSection = this.sectionDao.findEntity("from BBSSection s where s.id=?",company.getSectionID());
        int num;
        int sectionNum;
        if(join == 1) {
            num = careerTalk.getJoins() + 1;
            sectionNum = bbsSection.getJoins() + 1;
        }
        else {
            num = careerTalk.getJoins() - 1;
            sectionNum = bbsSection.getJoins() - 1;
            if(num < 0){
                num = 0;
                sectionNum = 0;
            }
        }
        careerTalk.setClicks(careerTalk.getClicks()+1);
        careerTalk.setJoins(num);
        this.careerTalkDao.update(careerTalk);
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
                BBSSection bs  = (BBSSection)it.next();
                if(bs.getId() != company.getSectionID()){
                    bbsSectionSet.add(bs);
                }
            }
        }
        user.setBbsSectionSet(bbsSectionSet);
        this.userDao.update(user);
    }

    public boolean setCareerTalk(String schoolName,String place,int companyId,String date,String time, String url,int db) throws Exception{
        if(companyId == 0)
            return false;
        CareerTalk careerTalk = new CareerTalk();
        Company company = this.companyDao.findEntity("from Company c where c.id=?",companyId);
        careerTalk.setDate(date);
        careerTalk.setTime(time);
        careerTalk.setPlace(place);
        careerTalk.setSchoolName(schoolName);
        careerTalk.setCompany(company);
        careerTalk.setCompanyName(company.getCompanyName());
        careerTalk.setInputTime(new Date());
        careerTalk.setUrl(url);
        if(db == 1)
            careerTalk.setSourceFrom("大街网");
        else if(db == 2)
            careerTalk.setSourceFrom("北邮");
        else if(db == 3)
            careerTalk.setSourceFrom("海投");

        BBSTopic topic = new BBSTopic();
        topic.setTitle(company.getCompanyName()+" "+schoolName);
        topic.setBody(date+" "+time+" "+place);
        topic.setCreatedTime(new Date());
        topic.setInputTime(new Date());
        topic.setBbsSection(this.sectionDao.findEntity("from BBSSection s where s.companyID=?",companyId));
        topic.setUserName("系统");
        this.topicDao.save(topic);
        careerTalk.setTopicID(topic.getId());
        this.careerTalkDao.save(careerTalk);
        BBSSection bbsSection = topic.getBbsSection();
        bbsSection.setTopics(bbsSection.getTopics()+1);
        this.sectionDao.update(bbsSection);
        return true;
    }

    public JSONObject getDetail(int careerTalkId) throws Exception{
        CareerTalk careerTalk = this.careerTalkDao.findEntity("from CareerTalk c where c.id=?",careerTalkId);
        careerTalk.setClicks(careerTalk.getClicks()+1);
        this.careerTalkDao.update(careerTalk);

        JSONObject fromObject = this.careerTalkUtil.parse(careerTalk);
        return fromObject;
    }
}
