package com.bupt.pcncad.controller.careerTalk;

import com.bupt.pcncad.service.careerTalk.ICareerTalkService;
import com.bupt.pcncad.service.company.ICompanyService;
import com.bupt.pcncad.service.search.ICompanySearchService;
import com.bupt.pcncad.service.source.ISourceService;
import com.bupt.pcncad.util.ParseDate;
import com.bupt.pcncad.util.ParseSchool;
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
 * Date: 13-8-25
 * Time: 下午10:06
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile/careerTalk")
public class CareerTalkController {
    @Autowired
    private ICareerTalkService careerTalkService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ISourceService sourceService;
    @Autowired
    private ICompanySearchService companySearchService;

    @RequestMapping("/list")
    public void sendCareerTalkList(ModelMap modelMap, @RequestParam(value = "userID") String userId,
                                   @RequestParam(value = "pageIndex")int pageIndex,
                                   @RequestParam(value = "order", required = false)Integer order,
                                   @RequestParam(value = "famous", required = false)Integer famous,
                                   @RequestParam(value = "province")String province,
                                   @RequestParam(value = "industry")String industry,
                                   @RequestParam(value = "type")String type,
                                   @RequestParam(value = "school", required = false)String school) throws Exception{
        order = (order == null) ?0 : order;
        famous = (famous == null) ? 0 : famous;     //0 全部 1 名企
        List<JSONObject> list = this.careerTalkService.getCareerTalkList(userId,pageIndex,order,famous,province,industry,type,school);
        modelMap.put("campusRecruit", list);
    }

    @RequestMapping("/click")
    public void sendCareerDetail(ModelMap modelMap, @RequestParam(value = "careerTalkID") int careerTalkId) throws Exception{
        this.careerTalkService.setCareerTalkClicks(careerTalkId);
    }

//    @RequestMapping("/test")
//    public void sendCareerTalkList(ModelMap modelMap) throws Exception{
//        String userId = 1+"";
//        List<CareerTalk> list = this.careerTalkService.getCareerTalkList(userId);
//        modelMap.put("careerTalkList", list);
//    }

    @RequestMapping("/join")
    public void joinCareerTalk(@RequestParam(value = "careerTalkID")int careerTalkId,
                               @RequestParam(value = "userID")String userId,
                               @RequestParam(value = "join")int join) throws Exception{
        this.careerTalkService.joinCareerTalk(userId,careerTalkId,join);
    }

    @RequestMapping("/setCareerTalk")
    public void inputCareerTalk(ModelMap modelMap,@RequestParam(value = "schoolName")String school,
                                @RequestParam(value = "place")String place,
                                @RequestParam(value = "companyId")int companyId,
                                @RequestParam(value = "date")String date,
                                @RequestParam(value = "time")String time,
                                @RequestParam(value = "sourceId")int sourceId,
                                @RequestParam(value = "url")String url,
                                @RequestParam(value = "db")int db) throws Exception{
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date start = format.parse(startTime.replaceAll("T"," "));
//        Date end = format.parse(endTime.replaceAll("T"," "));
        boolean success = false;
        if(db == 1)
            success = this.careerTalkService.setCareerTalk(ParseSchool.parse(school),place,companyId,ParseDate.parse(date,1),time,url,db);
        else if((db == 2) || (db == 3) )
            success = this.careerTalkService.setCareerTalk(ParseSchool.parse(school),place,companyId,ParseDate.parse(date,3),time,url,db);
        if(success)
            this.sourceService.setCTSource(sourceId, db);
        JSONObject source = this.sourceService.getCTSource();
        modelMap.put("source",source);
    }

    @RequestMapping("/search")
    public void search(ModelMap modelMap, @RequestParam(value = "companyName")String key) throws Exception{
//        List list = this.companyService.searchCompany(key, 1);
        List list = this.companySearchService.search(key,1);
        modelMap.put("campusRecruit",list);
    }

    @RequestMapping("/detail")
    public void getCareerTalkDetail(ModelMap modelMap, @RequestParam(value = "careerTalkID")int careerTalkId) throws Exception{
        JSONObject careerTalk = this.careerTalkService.getDetail(careerTalkId);
        modelMap.put("campusRecruit",careerTalk);
    }
}
