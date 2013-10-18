package com.bupt.pcncad.util;

import net.sf.json.JSONObject;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-21
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */
public class CompareUtil implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        JSONObject jsonObject1 = (JSONObject) o1;
        JSONObject jsonObject2 = (JSONObject) o2;
        String time1 = (String)jsonObject1.get("createdTime");
        String time2 =  (String)jsonObject2.get("createdTime");
        int flag = time1.compareTo(time2);
        return -flag;
    }
}
