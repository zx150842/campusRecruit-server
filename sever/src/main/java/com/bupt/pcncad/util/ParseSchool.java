package com.bupt.pcncad.util;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-10-16
 * Time: 下午11:34
 * To change this template use File | Settings | File Templates.
 */
public class ParseSchool {

    public static String parse(String school){
        if(school.equals("北交大"))
            school = "北京交通大学";
        else if(school.equals("北化"))
            school = "北京化工大学";
        else if(school.equals("北科大"))
            school = "北京科技大学";
        else if(school.equals("石大"))
            school = "中国石油大学";
        else if(school.equals("北师大"))
            school = "北京师范大学";
        else if(school.equals("人大"))
            school = "中国人民大学";
        else if(school.equals("矿大"))
            school = "中国矿业大学";
        else if(school.equals("北航"))
            school = "北京航空航天大学";
        else if(school.equals("华电"))
            school = "华北电力大学";
        else if(school.equals("农大"))
            school = "中国农业大学";
        else if(school.equals("地大"))
            school = "中国地质大学";
        else if(school.equals("北邮"))
            school = "北京邮电大学";
        else if(school.equals("广院"))
            school = "中国传媒大学";
        else if(school.equals("北理工"))
            school = "北京理工大学";
        else if(school.equals("清华"))
            school = "清华大学";
        else if(school.equals("中财"))
            school = "中央财经大学";
        else if(school.equals("北大"))
            school = "北京大学";
        else if(school.equals("民大"))
            school = "中央民族大学";
        return school;
    }
}
