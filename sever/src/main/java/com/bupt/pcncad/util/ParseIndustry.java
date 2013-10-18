package com.bupt.pcncad.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-15
 * Time: 下午5:10
 * To change this template use File | Settings | File Templates.
 */
public class ParseIndustry {
    private static final int JISUANJI = 1;
    private static final int DIANZI = 2;
    private static final int TONGXIN = 3;
    private static final int JINRONG = 4;

    public static String parse(String industry) {
        if(industry == null)
            return null;
        int temp = 0;
        String[] industrys = industry.split(",");
        String industryList = null;

        for(int i=0; i<industrys.length; i++) {
            if(industrys[i].equals("计算机"))
                temp = JISUANJI;
            else if(industrys[i].equals("电子"))
                temp = DIANZI;
            else if(industrys[i].equals("通信"))
                temp = TONGXIN;
            else if(industrys[i].equals("金融"))
                temp = JINRONG;
            if((i == 0) && (temp > 0))
                industryList = temp + "";
            else if(temp > 0)
                industryList = industryList + "," + temp;
        }
        return industryList;
    }

    public static String parse2industry(String industry){
        if(industry == null)
            return "";
        String[] industrys = industry.split(",");
        Set industrySet = new HashSet();
        for(int i=0; i<industrys.length; i++){
            industrySet.add(industrys[i]);
        }
        List list = new ArrayList(industrySet);
        String industryList = null;

        String temp = null;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            String industry_list = (String)list.get(i);
            if(industry_list.equals("1"))
                temp = "计算机";
            else if(industry_list.equals("2"))
                temp = "电子";
            else if(industry_list.equals("3"))
                temp = "通信";
            else if(industry_list.equals("4"))
                temp = "金融";
            if(i == 0)
                industryList = temp;
            else
                industryList = industryList + "," + temp;
        }
        return industryList;
    }
}
