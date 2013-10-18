package com.bupt.pcncad.service.util;

import com.bupt.pcncad.domain.CareerTalk;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-17
 * Time: 下午11:21
 * To change this template use File | Settings | File Templates.
 */
public interface ICareerTalkUtil {
    public List<CareerTalk> getCareerTalk(String userId, int pageIndex, int order, int famous,
                                          String r_province, String r_industry, String r_type, String school) throws Exception;
    public JSONObject parse(CareerTalk careerTalk);
}
