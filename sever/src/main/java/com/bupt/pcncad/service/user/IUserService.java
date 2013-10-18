package com.bupt.pcncad.service.user;

import com.bupt.pcncad.domain.Preference;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {
    public JSONObject loginCheck(String userId, String userPasswd) throws Exception;
    public String registerUser(String userId, String userAccount, String userPasswd) throws Exception;
    public Preference getUserPreference(String userId) throws Exception;
    public boolean setUserPreference(String userId, String province, String industry, String companyType, String notifyType, String sourceFrom) throws Exception;
    public JSONObject getUser(String userId) throws Exception;
    public void setUser(String userId, int gender, String schoolName, String major, String email) throws Exception;
    public List getReplies(String userId) throws Exception;
    public JSONObject getDetail(String userId) throws Exception;
    public List getAllComment(String userId) throws Exception;
    public int writeLetter(String fromUserId, String toUserId, String content) throws Exception;
    public List<JSONObject> getLetter(String userId, String otherUserId,String createdTime) throws Exception;
    public List getUserLetters(String userId) throws Exception;
    public List getUserTopics(String userId) throws Exception;
}
