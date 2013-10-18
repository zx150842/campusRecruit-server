package com.bupt.pcncad.service.careerTalk;

import com.bupt.pcncad.domain.CareerTalk;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-25
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public interface ICareerTalkService {
    public List<JSONObject> getCareerTalkList(String userId, int pageIndex, int order, int famous,
                                              String province, String industry, String type,String school) throws Exception;
    public CareerTalk getCareerTalkDetail(int careerTalkId) throws Exception;
    public void joinCareerTalk(String userId, int careerTalkId, int join) throws Exception;
    public boolean setCareerTalk(String school,String place,int companyId,String date, String time,String url,int db) throws Exception;
    public void setCareerTalkClicks(int careerTalkId) throws Exception;
    public JSONObject getDetail(int careerTalkId) throws Exception;
}
