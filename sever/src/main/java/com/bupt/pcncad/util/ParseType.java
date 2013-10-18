package com.bupt.pcncad.util;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-15
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class ParseType {
    private static final int GUOQI = 1;
    private static final int SIQI = 2;
    private static final int WAIQI = 3;

    public static int parse(String type) {
        int temp = 0;
        if(type.equals("国企"))
            temp = GUOQI;
        else if(type.equals("私企"))
            temp = SIQI;
        else if(type.equals("外企"))
            temp = WAIQI;
        return temp;
    }

    public static String parse2type(int type){
        if(type == 0)
            return 0+"";
        String temp = "";

        if(type == GUOQI)
            temp = "国企";
        else if(type == SIQI)
            temp = "私企";
        else if(type == WAIQI)
            temp = "外企";
        return temp;
    }
}
