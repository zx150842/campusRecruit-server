package com.bupt.pcncad.service.bbs;

import com.bupt.pcncad.dao.bbsreply.IBBSReplyDao;
import com.bupt.pcncad.dao.bbssection.IBBSSectionDao;
import com.bupt.pcncad.dao.bbstopic.IBBSTopicDao;
import com.bupt.pcncad.dao.careerTalk.ICareerTalkDao;
import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.recruit.IRecruitDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.*;
import com.bupt.pcncad.util.ParseIndustry;
import com.bupt.pcncad.util.ParseType;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 上午12:41
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BBSServiceImpl implements IBBSService {

    @Autowired
    private IBBSTopicDao bbsTopicDao;
    @Autowired
    private IBBSSectionDao bbsSectionDao;
    @Autowired
    private IBBSReplyDao bbsReplyDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private ICareerTalkDao careerTalkDao;
    @Autowired
    private IRecruitDao recruitDao;
    private int PAGE_SIZE = 20;

    public List<JSONObject> getTopicList(int sectionId, int companyId, int pageIndex, String userId) throws Exception{
        int new_sectionId = sectionId;
        if(sectionId == 0 && companyId != 0){
            Company company = this.companyDao.findEntity("from Company c where c.id=?",companyId);
            new_sectionId = company.getSectionID();
        }
        else if(sectionId == 0 && companyId == 0)
            return null;
        final int fSectionId = new_sectionId;
        final int pageNo = pageIndex;

        String temp_sql = "select * from BBS_TOPIC topic where " +
                "topic.section_id=? order by topic.last_reply_time desc limit ?,20";
        StringBuilder sb = new StringBuilder();
        sb.append(temp_sql);
//        if( pageIndex == 0){
//            sb.append(" order by topic.created_time desc limit 20");
//        }
//        else {
//            sb.append(" and topic.created_time < u.last_read_topic order by topic.created_time desc limit 20");
//        }
        final String sql = sb.toString();

        List<BBSTopic> bbsTopicList = this.bbsTopicDao.getByCustom(new HibernateCallback<List<BBSTopic>>() {
            @Override
            public List<BBSTopic> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(BBSTopic.class);
                query.setInteger(0,fSectionId);
                query.setInteger(1, pageNo*PAGE_SIZE);
                List pusList = query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        if(pageIndex == 0)
            if(bbsTopicList.size() > 0){
                User user = this.userDao.findEntity("from User u where u.userId=?",userId);
                if(user.getUsrLastReadTopic() < bbsTopicList.get(0).getId()) {
                    user.setUsrLastReadTopic(bbsTopicList.get(0).getId());
                    this.userDao.update(user);
                }
            }
        List list = new ArrayList();
        for(int i=0; i<bbsTopicList.size(); i++){
            list.add(parseTopic(bbsTopicList.get(i)));
        }
//        if(bbsTopicList.size() > 0){
//            if(pageIndex == 0){
//                user.setUsrLastReadTalk(bbsTopicList.get(0).getCreatedTime());
//            }
//            user.setOldReadTalk(bbsTopicList.get(bbsTopicList.size()-1).getCreatedTime());
//            this.userDao.save(user);
//        }

        return list;
    }

    public List getReplyList(int topicId, int pageIndex) throws Exception{
        final int fTopicId = topicId;
        final int pageNo = pageIndex;
        final String sql = "select * from BBS_REPLY reply where " +
                "reply.topic_id=? order by reply.create_time asc limit ?,20";

        List bbsReplies = this.bbsReplyDao.getByCustom(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(BBSReply.class);
                query.setInteger(0,fTopicId);
                query.setInteger(1, pageNo * PAGE_SIZE);
                List pusList = query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        Iterator it = bbsReplies.iterator();
        List replyList = new ArrayList();
        while(it.hasNext()){
            BBSReply bbsReply = (BBSReply)it.next();
            List bbsReplyList = new ArrayList();
            if(bbsReply.getRefReplyIds() != null){
                String[] refReplyIds = bbsReply.getRefReplyIds().split(",");
                for(int i=0; i < refReplyIds.length; i++){
                    if(Integer.parseInt(refReplyIds[i]) != 0) {
                        BBSReply refBbsReply = this.bbsReplyDao.findEntity("from BBSReply r where r.id=?",Integer.parseInt(refReplyIds[i]));
                        bbsReplyList.add(parseReply((refBbsReply)));
                    }
                    else {
                        BBSTopic topic = this.bbsTopicDao.findEntity("from BBSTopic t where t.id=?",topicId);
                        bbsReplyList.add(parseReplyTopic(topic));
                    }
                }
            }
            List bbsreplyAndref = new ArrayList();
            bbsreplyAndref.add(parseReply(bbsReply));
            bbsreplyAndref.add(bbsReplyList);
            replyList.add(bbsreplyAndref);
        }
        return replyList;
    }

    private JSONObject parseReply(BBSReply reply){
        Map map = new HashMap();
        map.put("id",reply.getId());
        map.put("content",reply.getContent());
        if(reply.getUserName() == null)
            map.put("userName","");
        else
            map.put("userName",reply.getUserName());
        if(reply.getUser().getImgType() == null)
            map.put("face",0);       //没有头像
        else
            map.put("face",1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(reply.getCreatedTime());
        map.put("createdTime",time);
        map.put("userID",reply.getUser().getUserId());
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }

    private JSONObject parseTopic(BBSTopic topic){
        Map map = new HashMap();
        map.put("id",topic.getId());
        map.put("title",topic.getTitle());
        map.put("body",topic.getBody());
        map.put("clicks",topic.getClicks());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(topic.getCreatedTime());
        map.put("createdTime",time);
        if(topic.getUserName() == null)
            map.put("userName","");
        else
            map.put("userName",topic.getUserName());

        if(topic.getUser() == null){
            map.put("userID",0);     //系统发帖
            map.put("face",0);       //没有头像
        }
        else {
            map.put("userID",topic.getUser().getUserId());
            if(topic.getUser().getImgType() == null)
                map.put("face",0);       //没有头像
            else
                map.put("face",1);
        }

        if(topic.getLastReplyTime() == null)
            map.put("lastReplyTime","");
        else{
            String reply_time = sdf.format(topic.getLastReplyTime());
            map.put("lastReplyTime",reply_time);
        }
        map.put("replies",topic.getReplies());
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }

    //flag 0 收藏 1 全部 2 名企       order 0 收藏 1 回帖
    public List getSectionList(String userId,int flag,int pageIndex, int order) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
//        Set<BBSSection> bbsSectionSet = null;
        List<BBSSection> sectionList = new ArrayList<BBSSection>();
        if(flag == 0) {
            Set<BBSSection> bbsSectionSet = user.getBbsSectionSet();
            Iterator it = bbsSectionSet.iterator();
            while(it.hasNext()){
                BBSSection bbsSection = (BBSSection) it.next();
                sectionList.add(bbsSection);
            }
            if(order == 0) {
                Collections.sort(sectionList, new Comparator() {
                    public int compare(Object a, Object b) {
                        int one = ((BBSSection)a).getJoins ();
                        int two = ((BBSSection)b).getJoins ();
                        return two - one ;
                    }
                });
            }
            else if(order == 1){
                Collections.sort(sectionList, new Comparator() {
                    public int compare(Object a, Object b) {
                        int one = ((BBSSection)a).getTopics ();
                        int two = ((BBSSection)b).getTopics ();
                        return two - one ;
                    }
                });
            }
        }

        else {
            final int pageNo = pageIndex;
            String temp_sql = "select * from BBS_SECTION s";
            StringBuilder sb = new StringBuilder();
            sb.append(temp_sql);
            if(order == 0)
                sb.append(" order by s.joins desc limit ?,20");
            else if(order == 1)
                sb.append(" order by s.topics desc limit ?,20");
            final String sql = sb.toString();
            List<BBSSection> bbsSectionList = this.bbsSectionDao.getByCustom(new HibernateCallback<List<BBSSection>>() {
                @Override
                public List<BBSSection> doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createSQLQuery(sql).addEntity(BBSSection.class);
                    query.setInteger(0,pageNo * PAGE_SIZE);
                    List pusList = query.list();
                    return pusList;  //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            if(flag == 1){
                sectionList = bbsSectionList;
            }
            else if(flag == 2){
                List<BBSSection> list = new ArrayList();
                for(int i=0; i<bbsSectionList.size(); i++){
                    Company company = this.companyDao.findEntity("from Company c where c.id=?",bbsSectionList.get(i).getCompanyID());
                    if(company.getFamousCompany() == 1){
                        list.add(bbsSectionList.get(i));
                    }
                }
                sectionList = list;
            }
        }

        List list = new ArrayList();
        Iterator it = sectionList.iterator();
        while(it.hasNext()){
            BBSSection bbsSection = (BBSSection) it.next();
            int companyId = bbsSection.getCompanyID();
            Company company = this.companyDao.findEntity("from Company c where c.id=?",companyId);
            Map map = new HashMap();
            map.put("id",bbsSection.getId());
            map.put("joins",bbsSection.getJoins());
            map.put("sectionName",bbsSection.getSectionName());
            if(company.getIndustry() == null)
                map.put("companyIndustry","");
            else
                map.put("companyIndustry", ParseIndustry.parse2industry(company.getIndustry()));
            map.put("companyType", ParseType.parse2type(company.getType()));
            map.put("topics",bbsSection.getTopics());
            map.put("famous",company.getFamousCompany());
            Set<BBSSection> sectionSet = user.getBbsSectionSet();
            Iterator its = sectionSet.iterator();
            int isjoin = 0;
            while(its.hasNext()){
                BBSSection bs = (BBSSection)its.next();
                if(bs.getId() == bbsSection.getId()) {
                    isjoin = 1;
                    break;
                }
            }
            map.put("join",isjoin);
            JSONObject fromObject = JSONObject.fromObject(map);
            list.add(fromObject);
        }
        return list;
    }

    //加入讨论组
    public boolean joinSection(String userId, int companyId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        Company company = this.companyDao.findEntity("from Company c where c.id=?",companyId);
        BBSSection bbsSection = null;
        if(company.getSectionID() == 0){
            bbsSection = new BBSSection();
            bbsSection.setCompanyID(companyId);
            this.bbsSectionDao.save(bbsSection);
            company.setSectionID(bbsSection.getId());
            this.companyDao.update(company);
        }
        else
            bbsSection = this.bbsSectionDao.findEntity("from BBSSection s where s.id=?",company.getSectionID());
        Iterator it = user.getBbsSectionSet().iterator();
        Set<BBSSection> bbsSectionSet = new HashSet<BBSSection>();
        while(it.hasNext()){
            bbsSectionSet.add((BBSSection)it.next());
        }
        bbsSectionSet.add(bbsSection);
        user.setBbsSectionSet(bbsSectionSet);
        this.userDao.update(user);
        return true;
    }

    //发表新帖子
    public int writeNewTopic(int sectionId, int companyId, String userId, String topicTitle, String topicText) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        BBSSection bbsSection = null;
        if(sectionId == 0 && companyId > 0){
            Company company = this.companyDao.findEntity("from Company com where com.id=?",companyId);
            bbsSection = this.bbsSectionDao.findEntity("from BBSSection s where s.id=?",company.getSectionID());
        }
        else if(sectionId > 0)
            bbsSection = this.bbsSectionDao.findEntity("from BBSSection section where section.id=?",sectionId);
        else if(sectionId == 0 && companyId == 0)
            return 0;
        BBSTopic bbsTopic = new BBSTopic();
        bbsTopic.setUser(user);
        bbsTopic.setBbsSection(bbsSection);
        bbsTopic.setBody(topicText);
        bbsTopic.setTitle(topicTitle);
        bbsTopic.setCreatedTime(new Date());
        bbsTopic.setUserName(user.getUserName());
        this.bbsTopicDao.save(bbsTopic);                 //保存帖子
        bbsTopic.setLastReplyTime(bbsTopic.getCreatedTime());
        this.bbsTopicDao.update(bbsTopic);
        bbsSection.setTopics(bbsSection.getTopics()+1);
        this.bbsSectionDao.update(bbsSection);           //更新讨论组帖子数
        return bbsTopic.getId();
    }

    private JSONObject parseReplyTopic(BBSTopic topic) {
        Map map = new HashMap();
        map.put("id",topic.getId());
        map.put("content",topic.getBody());
        if(topic.getUserName() == null)
            map.put("userName","");
        else
            map.put("userName",topic.getUserName());
        if(topic.getUserName().equals("系统")) {
            map.put("face",0);
            map.put("userID",0);
        }
        else{
            if(topic.getUser().getImgType() == null)
                map.put("face",0);       //没有头像
            else
                map.put("face",1);
            map.put("userID",topic.getUser().getUserId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(topic.getCreatedTime());
        map.put("createdTime",time);
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }

    //发表新回复
    public List writeNewReply(int topicId, String userId, String replyContent,int refReplyId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        BBSTopic topic = this.bbsTopicDao.findEntity("from BBSTopic t where t.id=?",topicId);
        BBSReply bbsReply = new BBSReply();
        List newReply = new ArrayList();
        List<JSONObject> bbsReplyList = new ArrayList<JSONObject>();
        BBSReply refReply = null;
        if(refReplyId > 0){
            refReply = this.bbsReplyDao.findEntity("from BBSReply r where r.id=?",refReplyId);

            String refReplyReplyIds = refReply.getRefReplyIds();
            if(refReplyReplyIds != null){
                String[] replyIds = refReplyReplyIds.split(",");
                for(int i=0; i < replyIds.length; i++){
                    BBSReply refBbsReply = null;
                    if(Integer.parseInt(replyIds[i]) != 0)
                        refBbsReply = this.bbsReplyDao.findEntity("from BBSReply r where r.id=?",Integer.parseInt(replyIds[i]));
                    if(refBbsReply != null)
                        bbsReplyList.add(parseReply(refBbsReply));
                    else
                        bbsReplyList.add(parseReplyTopic(topic));
                }
            }
            bbsReplyList.add(parseReply(refReply));
            StringBuffer sb = new StringBuffer();
            if(refReplyReplyIds == null)
                sb.append(refReplyId);
            else
                sb.append(refReplyReplyIds + "," + refReplyId);
            bbsReply.setRefReplyIds(sb.toString());
        }
        else if(refReplyId == -1){
            bbsReply.setRefReplyIds("0");
            bbsReplyList.add(parseReplyTopic(topic));
        }
        bbsReply.setContent(replyContent);
        bbsReply.setCreatedTime(new Date());
        bbsReply.setUser(user);
        bbsReply.setTopic(topic);
        bbsReply.setUserName(user.getUserName());
        this.bbsReplyDao.save(bbsReply);

        if(refReply != null){
            String replies = bbsReply.getId() + "";
            if(refReply.getBeReply() != null)
                replies = refReply.getBeReply() + "," + bbsReply.getId();
            refReply.setBeReply(replies);       //保存回复人帖子id
            this.bbsReplyDao.update(refReply);
        }

        topic.setLastReplyTime(bbsReply.getCreatedTime());
        topic.setReplies(topic.getReplies()+1);
        this.bbsTopicDao.update(topic);
        CareerTalk careerTalk = this.careerTalkDao.findEntity("from CareerTalk c where c.topicID=?",topicId);
        if(careerTalk != null){
            careerTalk.setReplies(careerTalk.getReplies()+1);      //更新宣讲会帖子回复数
            this.careerTalkDao.update(careerTalk);
        }
        Job job = this.recruitDao.findEntity("from Job j where j.topicID=?",topicId);
        if(job != null){
            job.setReplies(job.getReplies()+1);                   //更新校园招聘帖子回复数
            this.recruitDao.update(job);
        }
        newReply.add(parseReply(bbsReply));
//        if(bbsReplyList.size() > 0)
        newReply.add(bbsReplyList);
        return newReply;
    }

    public JSONObject getTopicDetail(int topicId) throws Exception{
        BBSTopic topic = this.bbsTopicDao.findEntity("from BBSTopic t where t.id=?",topicId);
        topic.setClicks(topic.getClicks()+1);
        this.bbsTopicDao.update(topic);
        return parseTopic(topic);
    }

    public void deleteSection(String userId, int sectionId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        if(user == null)
            return;
        Set<BBSSection> sectionSet = user.getBbsSectionSet();
        Iterator it = sectionSet.iterator();
        Set<BBSSection> bbsSectionSet = new HashSet<BBSSection>();
        while(it.hasNext()){
            BBSSection user_section = (BBSSection) it.next();
            if(user_section.getId() != sectionId)
                bbsSectionSet.add(user_section);
        }
        user.setBbsSectionSet(bbsSectionSet);
        this.userDao.update(user);
    }
}
