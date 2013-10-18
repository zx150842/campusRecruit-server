package com.bupt.pcncad.controller.notice;

import com.bupt.pcncad.service.notice.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-28
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    @RequestMapping("/notice")
    public void getNotice(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        List noticeList = this.noticeService.getNotice(userId);
        modelMap.put("campusRecruit",noticeList);
    }
}
