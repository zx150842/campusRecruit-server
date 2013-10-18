package com.bupt.pcncad.service.util.http;

import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.util.ParseProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-10-17
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SearchCompanyInBaike {

    @Autowired
    private ICompanyDao companyDao;

    private String rootUrl = "http://baike.baidu.com/search/word?pic=1&sug=1&enc=utf8&word=";
    private Pattern patternName = Pattern.compile("<h1.*?>(.*?)</h1>");
    private Pattern patternIntro = Pattern.compile("<div class=\"para\">(.*?)</div>");

    public void searchCompany(String keywords) throws Exception{
        String key = java.net.URLEncoder.encode(keywords, "utf-8");
        String url = this.rootUrl + key;
        System.out.println(url);
        String html = this.requestPage(url);
        Matcher matcherName = patternName.matcher(html);
        boolean find = matcherName.find();
        if(!find) {
            System.out.println("------------not found------------");
            System.out.println("-------------end------------------");
            return;
        }
        String companyName = matcherName.group(1);
        System.out.println(companyName);

        Matcher matcherIntro = patternIntro.matcher(html);
        matcherIntro.find();
        String intro = matcherIntro.group(1);
        String companyIntro = intro.replaceAll("<[^>]+>", "");//去除HTML标签的正则表达式
        System.out.println(companyIntro);
        String[] type = {"中","国家","联通","电信","石油","移动"};
        String[] computer = {"软件" ,"硬件" ,"计算机", "游戏" ,"测试" ,"手机" ,"互联网" ,"测试" ,"技术"};
        String[] electron = {"嵌入式" ,"电路", "自动化"};
        String[] communication = {"路由", "信号" ,"网络", "交换"};
        String[] financial = {"银行"};
        int companyType = 2;
        String industry = null;
        for(int i=0; i<type.length; i++){
            if(companyIntro.indexOf(type[i]) != -1) {
                companyType = 1;
                break;
            }
        }
        String str = companyIntro.toLowerCase();// 转换成小写
        Pattern expression = Pattern.compile("[a-zA-Z]+");// 定义正则表达式匹配单词
        Matcher matcher = expression.matcher(str);// 定义string1的匹配器
        int n = 0;// 文章中单词总数
        while (matcher.find()) {// 是否匹配单词
            n++;// 单词数加1
        }
        if(n > 10){
            companyType = 3;
        }

        for(int i=0; i<computer.length; i++){
            if(companyIntro.indexOf(computer[i]) != -1) {
                if(industry == null)
                    industry = 1 + ",";
                else
                    industry = industry + 1 + ",";
                break;
            }
        }
        for(int i=0; i<electron.length; i++){
            if(companyIntro.indexOf(electron[i]) != -1) {
                if(industry == null)
                    industry = 2 + ",";
                else
                    industry = industry + 2 + ",";
                break;
            }
        }
        for(int i=0; i<communication.length; i++){
            if(companyIntro.indexOf(communication[i]) != -1) {
                if(industry == null)
                    industry = 3 + ",";
                else
                    industry = industry + 3 + ",";
                break;
            }
        }
        for(int i=0; i<financial.length; i++){
            if(companyIntro.indexOf(financial[i]) != -1) {
                if(industry == null)
                    industry = 4 + ",";
                else
                    industry = industry + 4 + ",";
                break;
            }
        }
        System.out.println("------------"+companyType);
        if(industry == null)
            industry = "1,2,3,4";
        else
            industry = industry.substring(0, industry.length() - 1);
        System.out.println("------------"+industry);
        String province = ParseProvince.parse("全国");
        System.out.println(province);
        System.out.println("-------------end------------------");
    }

    private String requestPage(String link) throws Exception{
        URL url = new URL(link);
        HttpURLConnection request = (HttpURLConnection)url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
        String line;
        while((line = bufferdReader.readLine())!=null){
            sb.append(line.trim());
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }
}
