package com.bupt.pcncad.service.user;

import com.bupt.pcncad.dao.bbsreply.IBBSReplyDao;
import com.bupt.pcncad.dao.bbstopic.IBBSTopicDao;
import com.bupt.pcncad.dao.major.IMajorDao;
import com.bupt.pcncad.dao.preference.IPreferenceDao;
import com.bupt.pcncad.dao.privateLetter.IPrivateLetterDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.*;
import com.bupt.pcncad.util.CompareUtil;
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
 * Date: 13-8-26
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IPreferenceDao preferenceDao;
    @Autowired
    private IMajorDao majorDao;
    @Autowired
    private IBBSReplyDao replyDao;
    @Autowired
    private IBBSTopicDao topicDao;
    @Autowired
    private IPrivateLetterDao privateLetterDao;

    public JSONObject loginCheck(String userName, String userPasswd) throws Exception{
        String name = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
        User user = this.userDao.findEntity("from User u where u.userName=? and u.userPassword=?",name,userPasswd);
        if(user == null)
            return null;
        JSONObject fromObject = this.parse(user);
        return fromObject;
    }

    public JSONObject getDetail(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        JSONObject fromObject = this.parse(user);
        return fromObject;
    }

    public JSONObject getUser(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        JSONObject jsonObject = this.parse(user);
        return jsonObject;
    }

    public String registerUser(String userId, String userName, String userPasswd) throws Exception{
        if(userId == null)
            return 0+"";
        String name = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
        User user = this.userDao.findEntity("from User u where u.userName=?",name);
        if(user != null)
            return 0+"";
        else{
            User u = this.userDao.findEntity("from User u where u.userId=?",userId);
            if(u != null){
                u.setUserName(name);
                u.setUserPassword(userPasswd);
                u.setInputTime(new Date());
                this.userDao.update(u);
            }
            else{
                user = new User();
                user.setUserName(name);
                user.setUserPassword(userPasswd);
                user.setUserId(userId);
                user.setInputTime(new Date());
                this.userDao.save(user);
            }
        }
        return 1+"";
    }

    public Preference getUserPreference(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        Preference preference = null;
        if(user != null){
            preference = user.getPreference();
        }
        return preference;
    }

    public boolean setUserPreference(String userId, String province, String industry, String companyType, String notifyType, String sourceFrom) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?", userId);
        Preference preference;
        if(user != null){
            preference = user.getPreference();
            if(preference == null){
                preference = new Preference();
            }
        }
        else{
            user = new User();
            user.setUserId(userId);
            preference = new Preference();
        }
        if(companyType != null)
            preference.setCompanyType(companyType);
        if(province != null)
            preference.setProvince(province);
        preference.setUser(user);
        if(industry != null)
            preference.setVocationSet(industry);
        if(notifyType == null)
            preference.setNotifyType("1,2,3,4");
        else
            preference.setNotifyType(notifyType);
        if(sourceFrom == null)
            preference.setSourceFrom("0");
        else
            preference.setSourceFrom(sourceFrom);
        this.preferenceDao.save(preference);
        user.setPreference(preference);
        this.userDao.save(user);
        return true;
    }

    public void setUser(String userId, int gender, String schoolName, String major, String email) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        if(user == null)
            return;
        user.setGender(gender);
        user.setEmail(email);
        user.setSchoolName(schoolName);
        Major m = this.majorDao.findEntity("from Major m where m.majorName=?",major);
        if(m == null){
            m = new Major();
            m.setMajorName(major);
            this.majorDao.save(m);
        }
        user.setMajor(m);
        this.userDao.update(user);
    }

    public List getReplies(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        if(user == null)
            return null;
        int lastReadReply = user.getUsrLastReadReply();
        Set<BBSReply> bbsReplySet = user.getBbsReplySet();
        List bbsReplyList = new ArrayList();
        Iterator it = bbsReplySet.iterator();
        int maxReplyId = 0;
        while(it.hasNext()){
            BBSReply bbsReply = (BBSReply)it.next();
            if(bbsReply.getBeReply() != null && !bbsReply.getBeReply().equals("0") && !bbsReply.getBeReply().equals("")){

                String[] replies = bbsReply.getBeReply().split(",");
                int len = replies.length;
                for(int i=0; i<len; i++){
                    if(replies[i] == null || replies[i].equals(""))
                        continue;
                    BBSReply reply = this.replyDao.findEntity("from BBSReply r where r.id=?", Integer.parseInt(replies[i]));
                    Map map = new HashMap();
                    map.put("content",reply.getContent());
                    map.put("mycontent",bbsReply.getContent());
                    map.put("userID",reply.getUser().getUserId());
                    map.put("userName",reply.getUserName());
                    if(reply.getUser().getImgType() == null)
                        map.put("face",0);       //没有头像
                    else
                        map.put("face",1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(reply.getCreatedTime());
                    map.put("createdTime",time);
                    map.put("topicID",reply.getTopic().getId());
                    map.put("replyID",reply.getId());
                    JSONObject fromObject = JSONObject.fromObject(map);
                    bbsReplyList.add(fromObject);
//                    System.out.println(fromObject.get("createdTime"));
                }
                if(bbsReply.getId() > maxReplyId)
                    maxReplyId = bbsReply.getId();
            }
        }
        if(maxReplyId > lastReadReply){
            user.setUsrLastReadReply(maxReplyId);
            this.userDao.update(user);
        }
        CompareUtil comparator=new CompareUtil();
        Collections.sort(bbsReplyList, comparator);
        return bbsReplyList;
    }

    public List getAllComment(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        final String id = user.getId();
        final String sql = "select * from BBS_REPLY r where r.author_id=? order by r.create_time desc";
        List<BBSReply> bbsReplyList = this.replyDao.getByCustom(new HibernateCallback<List<BBSReply>>() {
            @Override
            public List<BBSReply> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(BBSReply.class);
                query.setString(0,id);
                List pusList =query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
//        List<BBSReply> bbsReplyList = this.replyDao.find("from BBSReply r where r.user.userId=?",userId);
        List list = new ArrayList();
        for(int i=0; i<bbsReplyList.size(); i++){
            if(bbsReplyList.get(i).getRefReplyIds() != null) {
                String[] reply = bbsReplyList.get(i).getRefReplyIds().split(",");
                String replyId = reply[reply.length-1];
                BBSReply bbsReply = this.replyDao.findEntity("from BBSReply r where r.id=?",Integer.parseInt(replyId));
                Map map = new HashMap();
                map.put("content",bbsReply.getContent());
                map.put("mycontent",bbsReplyList.get(i).getContent());
                map.put("userID",bbsReply.getUser().getUserId());
                if(bbsReply.getUser().getUserName() == null)
                    map.put("userName","");
                else
                    map.put("userName",bbsReply.getUser().getUserName());
                if(bbsReply.getUser().getImgType() == null)
                    map.put("face",0);       //没有头像
                else
                    map.put("face",1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(bbsReplyList.get(i).getCreatedTime());
                map.put("createdTime",time);
                map.put("topicID",bbsReply.getTopic().getId());
                JSONObject fromObject = JSONObject.fromObject(map);
                list.add(fromObject);
            }
        }
        return list;
    }

    public int writeLetter(String fromUserId, String toUserId, String content) throws Exception{
        User fromUser = this.userDao.findEntity("from User u where u.userId=?",fromUserId);
        User toUser = this.userDao.findEntity("from User u where u.userId=?",toUserId);
        if(fromUser == null || toUser == null){
            return 0;
        }
        PrivateLetter privateLetter = new PrivateLetter();
        if(content != null)
            privateLetter.setContent(content);
        privateLetter.setCreatedTime(new Date());
        privateLetter.setFromUserId(fromUser.getId());
        privateLetter.setToUserId(toUser.getId());
        privateLetter.setFromUserName(fromUser.getUserName());
        privateLetter.setToUserName(toUser.getUserName());
        this.privateLetterDao.save(privateLetter);
        return privateLetter.getId();
    }

    //私信
    public List<JSONObject> getLetter(String userId, String otherUserId, String createdTime) throws Exception{
        if(userId == null || otherUserId == null)
            return null;
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        User otherUser = this.userDao.findEntity("from User u where u.userId=?",otherUserId);
        if(user == null || otherUser == null )
            return null;
        final String uId = user.getId();
        final String oId = otherUser.getId();
        final String date = createdTime;
        final String sql = "select * from PRIVATE_LETTER where created_time>str_to_date(?,'%Y-%m-%d %H:%i:%s') and" +
                " ((from_user_id=? and to_user_id=?) or" +
                " (from_user_id=? and to_user_id=?)) order by created_time asc";

        List<PrivateLetter> privateLetterList = this.privateLetterDao.getByCustom(new HibernateCallback<List<PrivateLetter>>() {
            @Override
            public List<PrivateLetter> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(PrivateLetter.class);
                query.setString(0,date);
                query.setString(1,uId);
                query.setString(2,oId);
                query.setString(3,oId);
                query.setString(4,uId);
                List pusList = query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        List list = new ArrayList();
        int maxId = 0;
        for(int i=0; i<privateLetterList.size(); i++){
            PrivateLetter privateLetter = privateLetterList.get(i);
            Map map = new HashMap();
            map.put("id",privateLetter.getId());
            User fromUser = this.userDao.findEntity("from User u where u.id=?",privateLetter.getFromUserId());
            map.put("userID",fromUser.getUserId());
            if(privateLetter.getFromUserName() == null)
                map.put("userName","");
            else
                map.put("userName",privateLetter.getFromUserName());
            if(fromUser.getImgType() == null)
                map.put("face",0);       //没有头像
            else
                map.put("face",1);
            map.put("content",privateLetter.getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(privateLetter.getCreatedTime());
            map.put("createdTime",time);
            JSONObject fromObject = JSONObject.fromObject(map);
            list.add(fromObject);
            if(maxId < privateLetter.getId())
                maxId = privateLetter.getId();
        }
        if(privateLetterList.size() > 0){
            if(user.getUsrLastReadLetter() < maxId){
                user.setUsrLastReadLetter(maxId);
                this.userDao.update(user);
            }
        }
        return list;
    }

    public List getUserLetters(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        if(user == null)
            return null;
        List<PrivateLetter> privateLetterList = this.privateLetterDao.find("from PrivateLetter pl " +
                "where pl.toUserId=?",user.getId());
        Map map = new HashMap();
        for(int i=0; i<privateLetterList.size(); i++){
            PrivateLetter privateLetter = privateLetterList.get(i);
            if(map.size() == 0)
                map.put(privateLetter.getFromUserId(),privateLetter.getCreatedTime());
            else {
                Iterator it = map.entrySet().iterator();
                int flag = 0;
                while(it.hasNext()){
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String)entry.getKey();
                    Date val = (Date)entry.getValue();
                    if(key.equals(privateLetter.getFromUserId())){
                        flag = 1;
                        int result = val.compareTo(privateLetter.getCreatedTime());
                        if(result < 0){
                            map.put(key,privateLetter.getCreatedTime());
                        }
                    }
                }
                if(flag == 0)
                    map.put(privateLetter.getFromUserId(),privateLetter.getCreatedTime());

            }
        }

        Iterator it = map.entrySet().iterator();
        Map json_map = new HashMap();
        List list = new ArrayList();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String)entry.getKey();
            Date val = (Date)entry.getValue();
            User from_user = this.userDao.findEntity("from User u where u.id=?",key);
            json_map.put("userName",from_user.getUserName());
            json_map.put("id",from_user.getUserId());
            if(from_user.getImgType() == null)
                json_map.put("face",0);       //没有头像
            else
                json_map.put("face",1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(val);
            json_map.put("createdTime",time);
            PrivateLetter pl = this.privateLetterDao.findEntity("from PrivateLetter pl where pl.fromUserId=?" +
                    " and createdTime=?",from_user.getId(),val);
            json_map.put("content",pl.getContent());
            JSONObject jsonObject = JSONObject.fromObject(json_map);
            list.add(jsonObject);
        }
        CompareUtil comparator=new CompareUtil();
        Collections.sort(list, comparator);
        return list;
    }

    public List getUserTopics(String userId) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        List topics = this.topicDao.find("from BBSTopic t where t.user.id=?",user.getId());
        return topics;
    }

    private JSONObject parse(User user) throws Exception{
        List<BBSTopic> topics = this.topicDao.find("from BBSTopic t where t.user.userId=? order by t.createdTime desc",user.getUserId());
        Preference preference = user.getPreference();
        List list = new ArrayList();
        for(int i=0; i<topics.size(); i++){
            Map topic_map  = new HashMap();
            topic_map.put("topicID",topics.get(i).getId());
            topic_map.put("title",topics.get(i).getTitle());
            list.add(topic_map);
        }
        Map map = new HashMap();
        map.put("id", user.getUserId());
        if(user.getUserName() == null)
            map.put("userName","");
        else
            map.put("userName",user.getUserName());
        if(user.getEmail() == null)
            map.put("email","");
        else
            map.put("email",user.getEmail());
        map.put("gender",user.getGender());
        if(user.getMajor() == null)
            map.put("majorName","");
        else
            map.put("majorName",user.getMajor().getMajorName());
        if(user.getSchoolName() == null)
            map.put("schoolName", "");
        else
            map.put("schoolName",user.getSchoolName());
        if(user.getIntroduction() == null)
            map.put("introduction","");
        else
            map.put("introduction",user.getIntroduction());
        if(list.size() == 0)
            map.put("topics","");
        else
            map.put("topics",list);
        if(user.getImgType() == null)
            map.put("face",0);       //没有头像
        else
            map.put("face",1);
        if(user.getPreference() == null){
            map.put("province","");
            map.put("companyType","");
            map.put("industry","");
            map.put("notifyType","");
        }
        else{
            map.put("province",preference.getProvince());
            map.put("companyType",preference.getCompanyType());
            map.put("industry",preference.getVocationSet());
            map.put("notifyType",preference.getNotifyType());
        }
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }
}
