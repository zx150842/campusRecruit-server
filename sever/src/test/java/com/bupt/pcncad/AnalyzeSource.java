package com.bupt.pcncad;

import com.bupt.pcncad.dao.source.IDajieSourceDao;
import com.bupt.pcncad.domain.source.DajieSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-10-18
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class AnalyzeSource {

    @Autowired
    private IDajieSourceDao dajieSourceDao;

    @Test
    public void separateSource() throws Exception{
        List<DajieSource> dajieSourceList = this.dajieSourceDao.find("from DajieSource s where s.insertFlag=0 and s.pid>=19304");
        for(int i=0; i<dajieSourceList.size(); i++){
            DajieSource dajieSource = dajieSourceList.get(i);
            String text = dajieSource.getJobdetails();
            Pattern companyName = Pattern.compile("<.*>.*(公司简介|公司介绍).*?<\\/.*>(.*?)\\n[\\s| ]*\\r");
            Matcher nameMatcher = companyName.matcher(text);
            boolean find = nameMatcher.find();
            String companyInfo = null;
            if(find) {
                companyInfo = nameMatcher.group(1);
                System.out.println(companyInfo);
            }
            else
                System.out.println("----------------------not found company name-----------");

            Pattern recruit = Pattern.compile("<(.*)>.*(招聘职位)<\\/(.*)>(.*?)\\n[\\s| ]*\\r");
            Matcher recruitMatcher = recruit.matcher(text);
            find = recruitMatcher.find();
            String recruitInfo = null;
            if(find){
                recruitInfo = recruitMatcher.group(1);
                System.out.println(recruitInfo);
            }
            else
                System.out.println("-----------------------not found recruit info");

            Pattern contact = Pattern.compile("<(.*)>.*(招聘流程)<\\/(.*)>(.*?)\\n[\\s| ]*\\r");
            Matcher contactMatcher = contact.matcher(text);
            find = contactMatcher.find();
            String contactInfo = null;
            if(find){
                contactInfo = contactMatcher.group(1);
                System.out.println(contactInfo);
            }
            else
                System.out.println("----------------------------not found contact info");
        }
    }
}
