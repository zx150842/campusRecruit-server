package com.bupt.pcncad.service.util;

import com.bupt.pcncad.domain.Job;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-17
 * Time: 下午11:35
 * To change this template use File | Settings | File Templates.
 */
public interface IRecruitUtil {
    public List<Job> getRecruit(String userId, int pageIndex, int order, int famous,
                                String r_province, String r_industry, String r_type, String r_source) throws Exception;
    public JSONObject parse(Job job) throws Exception;
}
