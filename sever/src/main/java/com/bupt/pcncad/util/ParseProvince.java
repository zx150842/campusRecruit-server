package com.bupt.pcncad.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* Created with IntelliJ IDEA.
* User: zhang
* Date: 13-9-1
* Time: 下午4:27
* To change this template use File | Settings | File Templates.
*/
public class ParseProvince {
    private static final int QUANGUO = 0;
    private static final int BEIJING = 1;
    private static final int TIANJIN = 2;
    private static final int SHANGHAI = 3;
    private static final int GANGDONG = 4;
    private static final int ZHEJIANG = 5;
    private static final int JIANGSU = 6;
    private static final int SICHUAN = 7;
    private static final int SHANDONG = 8;
    private static final int HUNAN = 9;
    private static final int HUBEI = 10;
    private static final int HEINAN = 11;
    private static final int HEIBEI = 12;
    private static final int CHONGQING = 13;
    private static final int LIAONING = 14;
    private static final int ANHUI = 15;
    private static final int GUANGXI = 16;
    private static final int SHANXI = 17;
    private static final int JILIN = 18;
    private static final int HEILONGJIANG = 19;
    private static final int YUNNAN = 20;
    private static final int GUIZHOU = 21;
    private static final int XIZANG = 22;
    private static final int NINGXIA = 23;
    private static final int GANSU = 24;
    private static final int QINGHAI = 25;
    private static final int SHAANXI = 26;
    private static final int NEIMENGGU = 27;
    private static final int HAINAN = 28;
    private static final int JIANGXI = 29;
    private static final int XINJIANG = 30;
    private static final int FUJIAN = 31;
    private static final int GANGAOTAI = 32;
    private static final int HAIWAI = 33;

    public static String parse(String province){
        if(province == null)
            return null;
        String[] provinces = province.split(",");
        String provinceList = null;

        int temp = -1;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < provinces.length; i++){
            if(provinces[i].equals("北京"))
                temp = BEIJING;
            else if(provinces[i].equals("天津"))
                temp = TIANJIN;
            else if(provinces[i].equals("上海"))
                temp = SHANGHAI;
            else if(provinces[i].equals("广东"))
                temp = GANGDONG;
            else if(provinces[i].equals("浙江"))
                temp = ZHEJIANG;
            else if(provinces[i].equals("江苏"))
                temp = JIANGSU;
            else if(provinces[i].equals("四川"))
                temp = SICHUAN;
            else if(provinces[i].equals("山东"))
                temp = SHANDONG;
            else if(provinces[i].equals("湖南"))
                temp = HUNAN;
            else if(provinces[i].equals("湖北"))
                temp = HUBEI;
            else if(provinces[i].equals("河南"))
                temp = HEINAN;
            else if(provinces[i].equals("河北"))
                temp = HEIBEI;
            else if(provinces[i].equals("重庆"))
                temp = CHONGQING;
            else if(provinces[i].equals("辽宁"))
                temp = LIAONING;
            else if(provinces[i].equals("安徽"))
                temp = ANHUI;
            else if(provinces[i].equals("广西"))
                temp = GUANGXI;
            else if(provinces[i].equals("山西"))
                temp = SHANXI;
            else if(provinces[i].equals("吉林"))
                temp = JILIN;
            else if(provinces[i].equals("黑龙江"))
                temp = HEILONGJIANG;
            else if(provinces[i].equals("云南"))
                temp = YUNNAN;
            else if(provinces[i].equals("贵州"))
                temp = GUIZHOU;
            else if(provinces[i].equals("西藏"))
                temp = XIZANG;
            else if(provinces[i].equals("宁夏"))
                temp = NINGXIA;
            else if(provinces[i].equals("甘肃"))
                temp = GANSU;
            else if(provinces[i].equals("青海"))
                temp = QINGHAI;
            else if(provinces[i].equals("陕西"))
                temp = SHAANXI;
            else if(provinces[i].equals("内蒙古"))
                temp = NEIMENGGU;
            else if(provinces[i].equals("海南"))
                temp = HAINAN;
            else if(provinces[i].equals("江西"))
                temp = JIANGXI;
            else if(provinces[i].equals("新疆"))
                temp = XINJIANG;
            else if(provinces[i].equals("福建"))
                temp = FUJIAN;
            else if(provinces[i].equals("港澳台"))
                temp = GANGAOTAI;
            else if(provinces[i].equals("海外"))
                temp = HAIWAI;
            else if(provinces[i].equals("全国"))
                temp = QUANGUO;
            if((i == 0) && (temp >= 0))
                provinceList = temp + "";
            else if(temp >= 0)
                provinceList = provinceList + "," + temp;
        }
        if(provinceList == null)
            provinceList="0";
        if(provinceList.equals("0"))
            for(int i=0; i<34; i++) {
                if(i == 0)
                    provinceList = i +"";
                else
                    provinceList = provinceList + "," + i;
            }
        return provinceList;
    }

    public static String parse2province(String str) throws Exception{
        String[] provinces = str.split(",");
        Set provinceSet = new HashSet();
        for(int i=0; i<provinces.length; i++){
            provinceSet.add(provinces[i]);
        }
        List list = new ArrayList(provinceSet);
        String provinceList = null;

        String temp = null;
        int flag = 0;
        for(int i = 0; i < list.size(); i++){
            String province = (String)list.get(i);
            if(province.equals("1"))
                temp = "北京";
            else if(province.equals("2"))
                temp = "天津";
            else if(province.equals("3"))
                temp = "上海";
            else if(province.equals("4"))
                temp = "广东";
            else if(province.equals("5"))
                temp = "浙江";
            else if(province.equals("6"))
                temp = "江苏";
            else if(province.equals("7"))
                temp = "四川";
            else if(province.equals("8"))
                temp = "山东";
            else if(province.equals("9"))
                temp = "湖南";
            else if(province.equals("10"))
                temp = "湖北";
            else if(province.equals("11"))
                temp = "河南";
            else if(province.equals("12"))
                temp = "河北";
            else if(province.equals("13"))
                temp = "重庆";
            else if(province.equals("14"))
                temp = "辽宁";
            else if(province.equals("15"))
                temp = "安徽";
            else if(province.equals("16"))
                temp = "广西";
            else if(province.equals("17"))
                temp = "山西";
            else if(province.equals("18"))
                temp = "吉林";
            else if(province.equals("19"))
                temp = "黑龙江";
            else if(province.equals("20"))
                temp = "云南";
            else if(province.equals("21"))
                temp = "贵州";
            else if(province.equals("22"))
                temp = "西藏";
            else if(province.equals("23"))
                temp = "宁夏";
            else if(province.equals("24"))
                temp = "甘肃";
            else if(province.equals("25"))
                temp = "青海";
            else if(province.equals("26"))
                temp = "陕西";
            else if(province.equals("27"))
                temp = "内蒙古";
            else if(province.equals("28"))
                temp = "海南";
            else if(province.equals("29"))
                temp = "江西";
            else if(province.equals("30"))
                temp = "新疆";
            else if(province.equals("31"))
                temp = "福建";
            else if(province.equals("32"))
                temp = "港澳台";
            else if(province.equals("33"))
                temp = "海外";
            else if(province.equals("0")) {
                temp = "全国";
                flag = 1;
            }
            if(i == 0)
                provinceList = temp;
            else if(province != null)
                provinceList = provinceList + "," + temp;
        }
        if(flag == 1)
            provinceList = "全国";
        return provinceList;
    }

    public static String judgeOperator(List<String> keys) throws Exception{
        int flag = 0;
        String operation = null;
        String province = null;
        for(int i=0; i<keys.size(); i++){
            if(findInProvince(keys.get(i))) {
                flag++;
                province = keys.get(i);
            }
            else if(findInOperator(keys.get(i))){
                flag++;
                operation = keys.get(i);
            }
        }
        if(flag == 2)
            return province + "," +operation;
        else
            return null;
    }

    private static boolean findInOperator(String key) throws Exception{
        if(key.equals("移动") || key.equals("联通") || key.equals("电信") ||
                key.equals("中国移动") || key.equals("中国联通") || key.equals("中国电信"))
            return true;
        else
            return false;
    }

    private static boolean findInProvince(String key) throws Exception{
        int flag = 0;
        if(key.equals("北京"))
            flag = 1;
        else if(key.equals("天津"))
            flag = 1;
        else if(key.equals("上海"))
            flag = 1;
        else if(key.equals("广东"))
            flag = 1;
        else if(key.equals("浙江"))
            flag = 1;
        else if(key.equals("江苏"))
            flag = 1;
        else if(key.equals("四川"))
            flag = 1;
        else if(key.equals("山东"))
            flag = 1;
        else if(key.equals("湖南"))
            flag = 1;
        else if(key.equals("湖北"))
            flag = 1;
        else if(key.equals("河南"))
            flag = 1;
        else if(key.equals("河北"))
            flag = 1;
        else if(key.equals("重庆"))
            flag = 1;
        else if(key.equals("辽宁"))
            flag = 1;
        else if(key.equals("安徽"))
            flag = 1;
        else if(key.equals("广西"))
            flag = 1;
        else if(key.equals("山西"))
            flag = 1;
        else if(key.equals("吉林"))
            flag = 1;
        else if(key.equals("黑龙江"))
            flag = 1;
        else if(key.equals("云南"))
            flag = 1;
        else if(key.equals("贵州"))
            flag = 1;
        else if(key.equals("西藏"))
            flag = 1;
        else if(key.equals("宁夏"))
            flag = 1;
        else if(key.equals("甘肃"))
            flag = 1;
        else if(key.equals("青海"))
            flag = 1;
        else if(key.equals("陕西"))
            flag = 1;
        else if(key.equals("内蒙古"))
            flag = 1;
        else if(key.equals("海南"))
            flag = 1;
        else if(key.equals("江西"))
            flag = 1;
        else if(key.equals("新疆"))
            flag = 1;
        else if(key.equals("福建"))
            flag = 1;
        if(flag == 1)
            return true;
        else
            return false;
    }
}
