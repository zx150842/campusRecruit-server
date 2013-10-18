package com.bupt.pcncad.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-11
 * Time: 下午3:55
 * To change this template use File | Settings | File Templates.
 */
public class ParseDate {

    public static String parse(String createdTime,int type) throws Exception{    //1 宣讲会 2 校园招聘  3北邮宣讲会
        Timestamp time = new Timestamp(new java.util.Date().getTime());
        String[] times = {null};
        if((type == 1) || (type == 3))
            times = createdTime.split("-");
        else if(type == 2)
            times = createdTime.split("[.]");
        StringBuilder sb = new StringBuilder();
        if(type < 3)
            sb.append("2013-");
        for(int i=0;i<times.length;i++){
            sb.append(times[i]);
            if(i != times.length - 1)
                sb.append("-");
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//时:分:秒
        sb.append(" "+sdf.format(d));
        return sb.toString();
    }
}
