package com.bupt.pcncad.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-31
 * Time: 上午12:16
 * To change this template use File | Settings | File Templates.
 */
public class ResourceUtil {

    public static String getRealSavePathByUserId(String userId){
        String savePath = null;
        try {
            savePath = transformToPathFromId(userId);
        } catch (IOException e) {
            e.printStackTrace();
//            LoggerUtil.error(ResourceUtil.class,"IOException");
            //To change body of catch statement use File | Settings | File Templates.
        }
//        LoggerUtil.debug(ResourceUtil.class,"resource上传存储路径为"+savePath);
        return savePath;
    }

    private static String transformToPathFromId(String userId) throws IOException {
        String str = userId.substring(26);
        StringBuilder path = new StringBuilder();
        for(int i=0;i<str.length();i++){
            path.append(str.charAt(i));
            if((i+1)%2==0){
                path.append(File.separator);
            }
        }
        Properties props = new Properties();
        props.load(ResourceUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        return (String)props.get("resourceRootPath")+File.separator+path.toString();
    }
}
