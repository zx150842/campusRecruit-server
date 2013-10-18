package com.bupt.pcncad.controller.recruit;

import com.bupt.pcncad.service.company.ICompanyService;
import com.bupt.pcncad.service.recruit.IRecruitService;
import com.bupt.pcncad.service.search.ICompanySearchService;
import com.bupt.pcncad.service.source.ISourceService;
import com.bupt.pcncad.service.user.IUserService;
import com.bupt.pcncad.util.ParseDate;
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
 * Date: 13-8-26
 * Time: 下午8:59
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile/recruit")
public class RecruitController {

    @Autowired
    private IRecruitService recruitService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISourceService sourceService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ICompanySearchService companySearchService;

    @RequestMapping("/list")
    public void getRecruitList(ModelMap modelMap,@RequestParam(value = "userID")String userId,
                               @RequestParam(value = "pageIndex")int pageIndex,
                               @RequestParam(value = "order")int order,
                               @RequestParam(value = "famous", required = false)Integer famous,
                               @RequestParam(value = "province")String province,
                               @RequestParam(value = "industry")String industry,
                               @RequestParam(value = "type")String type,
                               @RequestParam(value = "source")String source) throws Exception{
        famous = (famous == null) ? 0 : famous;
        List<JSONObject> positions = this.recruitService.getRecruitList(userId, pageIndex,order,famous, province, industry, type, source);
        modelMap.put("campusRecruit", positions);
    }

    @RequestMapping("/detail")
    public void getRecruitDetail(ModelMap model,@RequestParam(value = "recruitID")int recruitId) throws Exception{
        JSONObject detail = this.recruitService.getRecruitDetail(recruitId);
        model.put("campusRecruit",detail);
    }

    @RequestMapping("/join")
    public void joinRecruit(ModelMap model,@RequestParam(value = "recruitID")int recruitId,
                            @RequestParam(value = "userID")String userId,
                            @RequestParam(value = "join")int join) throws Exception{
        this.recruitService.joinRecruit(recruitId, join, userId);
    }

    @RequestMapping("/inputRecruit")
    public void inputRecruit(ModelMap modelMap,@RequestParam(value = "companyId")int companyId,
                             @RequestParam(value = "position")String position,
                             @RequestParam(value = "description")String description,
                             @RequestParam(value = "place")String place,
                             @RequestParam(value = "contact")String contact,
                             @RequestParam(value = "createdTime")String createdTime,
                             @RequestParam(value = "sourceId")int sourceId,
                             @RequestParam(value = "db")int db,
                             @RequestParam(value = "url")String url,
                             @RequestParam(value = "form")String form,
                             @RequestParam(value = "img")String img) throws Exception{
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date time = format.parse(createdTime);
        //db 1 大街 2 byr 3 水木
        place = (place == null) ? "全国" : place;
        boolean success = false;
        if(db == 1)
            success = this.recruitService.setRecruit(sourceId,companyId, position, description, ParseProvince.parse(place), contact, ParseDate.parse(createdTime,2),db,url,form,img);
        else
            success = this.recruitService.setRecruit(sourceId,companyId, position, description, ParseProvince.parse(place), contact, createdTime,db,url,form,img);
        if(success)
            this.sourceService.setSource(sourceId,db);
//        JSONObject source = this.sourceService.getSource();
//        modelMap.put("source",source);
    }

    @RequestMapping("/description")
    public void getDesc(ModelMap modelMap, @RequestParam(value = "recruitID")int recruitId) throws Exception{
        String desc = this.recruitService.getDesc(recruitId);
        modelMap.put("campusRecruit", desc);
    }

    @RequestMapping("/contact")
    public void getContact(ModelMap modelMap, @RequestParam(value = "recruitID")int recruitId) throws Exception{
        JSONObject jsonObject = this.recruitService.getContact(recruitId);
        modelMap.put("campusRecruit", jsonObject);
    }

    @RequestMapping("/search")
    public void search(ModelMap modelMap, @RequestParam(value = "companyName")String key) throws Exception{
//        List list = this.companyService.searchCompany(key, 2);
        List list = this.companySearchService.search(key,2);
        modelMap.put("campusRecruit",list);
    }

    @RequestMapping("/form")
    public void getForm(ModelMap modelMap, @RequestParam(value = "recruitID")int recruitId) throws Exception{
        String form = this.recruitService.getForm(recruitId);
        modelMap.put("campusRecruit",form);
    }

    @RequestMapping("/img")
    public void getImg(ModelMap modelMap, @RequestParam(value = "recruitID")int recruitId) throws Exception{
        String img = this.recruitService.getImg(recruitId);
        modelMap.put("campusRecruit",img);
    }

    @RequestMapping("/simpledetail")
    public void getSimpleDetail(ModelMap modelMap, @RequestParam(value = "recruitID")int recruitId) throws Exception{
        JSONObject jsonObject = this.recruitService.getSimpleDetail(recruitId);
        modelMap.put("campusRecruit",jsonObject);
    }
}
