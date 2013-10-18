package com.bupt.pcncad.service.notice;

import com.bupt.pcncad.dao.bbsreply.IBBSReplyDao;
import com.bupt.pcncad.dao.bbstopic.IBBSTopicDao;
import com.bupt.pcncad.dao.privateLetter.IPrivateLetterDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.*;
import com.bupt.pcncad.service.util.ICareerTalkUtil;
import com.bupt.pcncad.service.util.IRecruitUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 下午10:46
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IBBSTopicDao topicDao;
    @Autowired
    private IPrivateLetterDao privateLetterDao;
    @Autowired
    private IBBSReplyDao replyDao;
    @Autowired
    private ICareerTalkUtil careerTalkUtil;
    @Autowired
    private IRecruitUtil recruitUtil;

    public List getNotice(String userId) throws Exception{
        List noticeList = new ArrayList();
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        Preference preference = user.getPreference();
        String notifyType = preference.getNotifyType();
        int careerTalk_flag = 0;
        int recruit_flag = 0;
        int message_flag = 0;
        int reply_flag = 0;
        if(notifyType == null){
            careerTalk_flag = 1;
            recruit_flag = 1;
            message_flag = 1;
            reply_flag = 1;
        }
        final String[] notifyTypes = notifyType.split(",");


        for(int i=0; i<notifyTypes.length; i++){
            if(notifyTypes[i].equals("1"))
                careerTalk_flag = 1;
            else if(notifyTypes[i].equals("2"))
                recruit_flag = 1;
            else if(notifyTypes[i].equals("3"))
                message_flag = 1;
            else if(notifyTypes[i].equals("4"))
                reply_flag = 1;
        }

        //宣讲会通知
        List<CareerTalk> careerTalkList = new ArrayList<CareerTalk>();
        if(careerTalk_flag == 1)
            careerTalkList = this.careerTalkUtil.getCareerTalk(userId,-1,0,0,0+"",0+"",0+"", "全部");
        Map map = new HashMap();
        if(careerTalkList.size() == 0)
            map.put("CareerTalkCount",0);
        else{
            map.put("CareerTalkCount",careerTalkList.size());
        }

        JSONObject fromObject = JSONObject.fromObject(map);
        noticeList.add(fromObject);

        //校园招聘通知
        List<Job> jobs = new ArrayList<Job>();
        if(recruit_flag == 1)
            jobs = this.recruitUtil.getRecruit(userId,-1,0,0,0+"",0+"",0+"",0+"");
        map = new HashMap();
        if(jobs.size() == 0)
            map.put("RecruitCount",0);
        else {
            map.put("RecruitCount",jobs.size());
        }
        fromObject = JSONObject.fromObject(map);
        noticeList.add(fromObject);

        //私信通知
        List<PrivateLetter> letters = new ArrayList();
        if(message_flag == 1)
            letters = this.privateLetterDao.find("from PrivateLetter pl where pl.id>? and pl.toUserId=?",user.getUsrLastReadLetter(),user.getId());
        map = new HashMap();
        if(letters.size() == 0)
            map.put("MessageCount",0);
        else
            map.put("MessageCount",letters.size());
        fromObject = JSONObject.fromObject(map);
        noticeList.add(fromObject);

        if(letters.size() > 0){
            for(int i=0; i<letters.size(); i++){
                if(user.getUsrLastReadLetter() < letters.get(i).getId()){
                    user.setUsrLastReadLetter(letters.get(i).getId());
                    this.userDao.update(user);
                }
            }
        }

        //回帖通知
        int replyCount = 0;
        List<BBSReply> replyList = new ArrayList<BBSReply>();
        BBSReply one_reply = null;
        int max_reply_id = 0;
        if(reply_flag == 1) {
            replyList = this.replyDao.find("from BBSReply r where r.user.id=?",user.getId());
            for(int i=0; i<replyList.size(); i++){
                BBSReply reply = replyList.get(i);
                if(reply.getBeReply() != null) {
                    String[] replies = reply.getBeReply().split(",");
                    for(int j=0; j<replies.length; j++){
                        if(replies[j] != null && !replies[j].equals("0") && !replies[j].equals("")){
                            if(user.getUsrLastReadReply() < Integer.parseInt(replies[j])){
                                replyCount = replyCount + 1;
                                one_reply = this.replyDao.findEntity("from BBSReply r where r.id=?", Integer.parseInt(replies[j]));
                                if(max_reply_id < Integer.parseInt(replies[j]))
                                    max_reply_id = Integer.parseInt(replies[j]);
                            }
                        }
                    }
                }
            }
        }
        if(max_reply_id > user.getUsrLastReadReply()){
            user.setUsrLastReadReply(max_reply_id);
            this.userDao.update(user);
        }

//        for(int i=0; i<replyList.size(); i++){
//            BBSReply bbsReply = replyList.get(i);
//            if(bbsReply.getBeReply() != null && !bbsReply.getBeReply().equals("0") && !bbsReply.getBeReply().equals("")){
//                String[] replies = bbsReply.getBeReply().split(",");
//                int len = replies.length;
//                replyCount = replyCount + len;
//            }
//        }
        map = new HashMap();
        if(replyCount == 0)
            map.put("ReplyCount",0);
        else
            map.put("ReplyCount",replyCount);
        fromObject = JSONObject.fromObject(map);
        noticeList.add(fromObject);

//        if(replyList.size() > 0){
//            for(int i=0; i<replyList.size(); i++){
//                if(user.getUsrLastReadReply() < replyList.get(i).getId()){
//                    user.setUsrLastReadReply(replyList.get(i).getId());
//                    this.userDao.update(user);
//                }
//            }
//        }

        if(jobs.size() + careerTalkList.size() + letters.size() + replyCount == 1)  {
            if(jobs.size() == 1)
                noticeList.add(jobs.get(0));
            else if(careerTalkList.size() == 1)
                noticeList.add(careerTalkList.get(0));
            else if(letters.size() == 1){
                Map map1 = new HashMap();
                map1.put("userName",letters.get(0).getFromUserName());
                map1.put("content",letters.get(0).getContent());
                JSONObject jsonObject = JSONObject.fromObject(map1);
                noticeList.add(jsonObject);
            }
            else if(replyCount == 1){
                noticeList.add(one_reply);
//                for(int i=0; i<replyList.size(); i++){
//                    BBSReply bbsReply = replyList.get(i);
//                    if(bbsReply.getBeReply() != null && !bbsReply.getBeReply().equals("0") && !bbsReply.getBeReply().equals("")){
//                        String[] replies = bbsReply.getBeReply().split(",");
//                        BBSReply reply = this.replyDao.findEntity("from BBSReply r where r.id=?",Integer.parseInt(replies[0]));
//                        noticeList.add(reply);
//                        break;
//                    }
//                }
            }
        }
        return noticeList;
    }
}
