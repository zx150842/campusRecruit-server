package com.bupt.pcncad.controller.company;

import com.bupt.pcncad.domain.Company;
import com.bupt.pcncad.service.company.ICompanyService;
import com.bupt.pcncad.util.ParseProvince;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-4
 * Time: 上午12:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @RequestMapping("/company")
    public void getCompany(ModelMap modelMap, @RequestParam(value = "companyID")int companyId) throws Exception{
        JSONObject obj = this.companyService.getCompany(companyId);
        modelMap.put("campusRecruit", obj);
    }

    @RequestMapping("/inputCompany")
    public void inputCompany(ModelMap modelMap,@RequestParam(value = "companyName")String companyName,
                             @RequestParam(value = "type", required = false)Integer type,
                             @RequestParam(value = "province", required = false)String province,
                             @RequestParam(value = "info", required = false)String info,
                             @RequestParam(value = "computer", required = false)Integer computer,
                             @RequestParam(value = "electron", required = false)Integer electron,
                             @RequestParam(value = "communication", required = false)Integer communication,
                             @RequestParam(value = "financial",required = false)Integer financial,
                             @RequestParam(value = "state",required = false)String state,
                             @RequestParam(value = "statetime",required = false)String statetime,
                             @RequestParam(value = "famous", required = false)Integer famous,
                             @RequestParam(value = "pageType", required = false)Integer pageType) throws Exception{
        type = (type == null) ? 0 : type;
        famous = (famous == null) ? 0 : famous;
        computer = (computer == null) ? 0 : computer;
        electron = (electron == null) ? 0 :electron;
        communication = (communication == null) ? 0 : communication;
        financial = (financial == null) ? 0 : financial;
        pageType = (pageType == null) ? 2 : pageType;

//        province = (province == null) ? "全国" : province;
        StringBuilder sb = new StringBuilder();
        if(computer > 0)
            sb.append(computer);
        if(electron > 0){
            if(computer > 0)
                sb.append(","+electron);
            else
                sb.append(electron);
        }
        if(communication > 0) {
            if((computer > 0) || (electron > 0))
                sb.append(","+communication);
            else
                sb.append(communication);
        }
        if(financial > 0) {
            if((computer > 0) || (electron > 0) || (communication > 0))
                sb.append(","+financial);
            else
                sb.append(financial);
        }
        String industry = null;
        if((computer+electron+communication+financial) > 0)
            industry = sb.toString();
        int companyId = this.companyService.setCompany(companyName, type, ParseProvince.parse(province), info, industry, state, statetime,famous,pageType);
        modelMap.put("id",companyId);
        modelMap.put("name", companyName);
    }

    @RequestMapping("/search")
    public void search(ModelMap modelMap, @RequestParam(value = "key")String key) throws Exception{
        List<Company> companyList = this.companyService.searchCompany(key, 0);
        modelMap.put("campusRecruit",companyList);
    }
}
