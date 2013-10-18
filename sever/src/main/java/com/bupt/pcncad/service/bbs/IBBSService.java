package com.bupt.pcncad.service.bbs;

import com.bupt.pcncad.domain.BBSReply;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 上午12:40
 * To change this template use File | Settings | File Templates.
 */
public interface IBBSService {
    public List<JSONObject> getTopicList(int sectionId, int companyId, int pageIndex,String userId) throws Exception;
    public List<BBSReply> getReplyList(int topicId, int pageIndex) throws Exception;
    public List getSectionList(String userId,int flag, int pageIndex, int order) throws Exception;
    public int writeNewTopic(int sectionId, int companyId, String userId, String topicTitle, String topicText) throws Exception;
    public List writeNewReply(int topicId, String userId, String replyContent,int refReplyId) throws Exception;
    public boolean joinSection(String userId, int companyId) throws Exception;
    public JSONObject getTopicDetail(int topicId) throws Exception;
    public void deleteSection(String userId, int sectionId) throws Exception;
}
