package com.bupt.pcncad;

import com.bupt.pcncad.dao.source.IDajieCompanyDao;
import com.bupt.pcncad.domain.source.DajieCompany;
import com.bupt.pcncad.service.company.ICompanyService;
import com.bupt.pcncad.util.ParseIndustry;
import com.bupt.pcncad.util.ParseProvince;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-10-18
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class InputCompany {
    @Autowired
    private IDajieCompanyDao dajieCompanyDao;
    @Autowired
    private ICompanyService companyService;

    @Test
    public void inputCompany() throws Exception{
        List<DajieCompany> dajieCompanyList = this.dajieCompanyDao.find("from DajieCompany c where c.insertFlag=0");
        for(int i=0; i<dajieCompanyList.size(); i++){
            DajieCompany dajieCompany = dajieCompanyList.get(i);
            //this.companyService.setCompany(companyName, type, ParseProvince.parse(province), info, industry, state, statetime,famous,pageType);
            if(dajieCompany.getType().equals("暂无"))
                break;
            String companyName = dajieCompany.getName();
            int type = parseType(dajieCompany.getType());
            String province = dajieCompany.getLocal();
            String info = dajieCompany.getDetails();
            String industry = ParseIndustry.parse(dajieCompany.getNewIndustry());
            if(industry == null)
                industry = dajieCompany.getOldIndustry();
            int pageType = 2;
            String state = null;
            String statetime = null;
            int famous = 0;
            System.out.println("======================================================");
            System.out.println("name-----"+companyName);
            System.out.println("type-----"+type);
            System.out.println("province----"+ParseProvince.parse(province));
            System.out.println(info);
            System.out.println("industry-------"+industry);
//            System.out.println(state);
//            System.out.println(statetime);
//            System.out.println(famous);
            this.companyService.setCompany(companyName, type, ParseProvince.parse(province), info, industry, state, statetime,famous,pageType);
        }
    }

    private int parseType(String type) throws Exception{
        int new_type;
        if(type.equals("外资·合资"))
            new_type = 3;
        else if(type.equals("私营·股份制企业"))
            new_type = 2;
        else if(type.equals("国有企业") || type.equals("非营利·事业单位"))
            new_type = 1;
        else
            new_type = 2;
        return new_type;
    }
}
