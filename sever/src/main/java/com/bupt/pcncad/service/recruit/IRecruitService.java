package com.bupt.pcncad.service.recruit;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 下午7:42
 * To change this template use File | Settings | File Templates.
 */
public interface IRecruitService {
    public List<JSONObject> getRecruitList(String userId, int pageIndex,int order,int famous, String province,
                                           String industry, String type, String source) throws Exception;
    public JSONObject getRecruitDetail(int recruitId) throws Exception;
    public void joinRecruit(int recruitId, int join, String userId) throws Exception;
    public boolean setRecruit(int sourceId, int companyId, String position, String description, String place, String contact,
                           String createdTime,int db,String url,String form,String img) throws Exception;
    public String getDesc(int recruitId) throws Exception;
    public JSONObject getContact(int recruitId) throws Exception;
    public String getForm(int recruitId) throws Exception;
    public String getImg(int recruitId) throws Exception;
    public JSONObject getSimpleDetail(int recruitId) throws Exception;
}
