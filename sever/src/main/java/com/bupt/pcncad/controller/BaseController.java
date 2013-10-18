package com.bupt.pcncad.controller;

import com.bupt.pcncad.service.source.ISourceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-4
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaseController {

    @Autowired
    private ISourceService sourceService;

    @RequestMapping("/careertalk")
    public String careerTalk(ModelMap modelMap, @RequestParam(value = "ignore", required = false)Integer ignore,
                             @RequestParam(value = "sourceId", required = false)Integer sourceId,
                             @RequestParam(value = "db", required = false)Integer db) throws Exception{
        ignore = (ignore == null) ? 0 : ignore;
        if(ignore == 1){
            this.sourceService.setCTSource(sourceId, db);
        }
        JSONObject source = this.sourceService.getCTSource();
        modelMap.put("source", source);
//        return "upload/upload";
        return "careertalk/careertalk";
    }

    @RequestMapping("/campusrecruit")
    public String campusRecruit(ModelMap modelMap, @RequestParam(value = "ignore", required = false)Integer ignore,
                                @RequestParam(value = "sourceId", required = false)Integer sourceId,
                                @RequestParam(value = "db", required = false)Integer db) throws Exception {
        ignore = (ignore == null) ? 0 : ignore;
        db = (db == null) ? 0 : db;
        if(ignore == 1){
            this.sourceService.setSource(sourceId,db);
        }
        Object source = this.sourceService.getSource();
        modelMap.put("source", source);
        return "campusrecruit/recruit";
    }

}
