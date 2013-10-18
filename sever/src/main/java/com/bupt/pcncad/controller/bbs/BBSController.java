package com.bupt.pcncad.controller.bbs;

import com.bupt.pcncad.domain.BBSReply;
import com.bupt.pcncad.service.bbs.IBBSService;
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
 * Date: 13-8-27
 * Time: 上午12:46
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile")
public class BBSController {
    @Autowired
    private IBBSService bbsService;

    @RequestMapping("/bbsSection/list")
    public void bbsSectionList(ModelMap modelMap, @RequestParam(value = "userID")String userId,
                               @RequestParam(value = "flag", required = false)Integer flag,
                               @RequestParam(value = "pageIndex", required = false)Integer pageIndex,
                               @RequestParam(value = "order", required = false)Integer order) throws Exception{
        flag = (flag == null) ? 0 : flag;      //0 收藏 1 全部 2 名企
        order = (order == null) ? 0 : order;   //0 收藏 1 回帖
        pageIndex = (pageIndex == null) ? 0 : pageIndex;
        List bbsSectionSet = this.bbsService.getSectionList(userId,flag,pageIndex,order);
        modelMap.put("campusRecruit", bbsSectionSet);
    }

//    @RequestMapping("/bbsTopic/companyName")
//    public void bbsSectionListByCompanyName(ModelMap modelMap, @RequestParam(value = "companyID")int companyId,
//                                            @RequestParam(value = "pageIndex")int )

    @RequestMapping("/bbsTopic/list")
    public void bbsTopicList(ModelMap modelMap, @RequestParam(value = "sectionID", required = false)Integer sectionId,
                             @RequestParam(value = "companyID", required = false)Integer companyId,
                             @RequestParam(value = "pageIndex")int pageIndex,
                             @RequestParam(value = "userID")String userId) throws Exception{
        sectionId = (sectionId == null) ? 0 : sectionId;
        companyId = (companyId == null) ? 0 : companyId;
        List<JSONObject> bbsTopicList = this.bbsService.getTopicList(sectionId, companyId, pageIndex, userId);
        modelMap.put("campusRecruit", bbsTopicList);
    }

    @RequestMapping("/bbsTopic/detail")
    public void bbsTopicDetail(ModelMap modelMap, @RequestParam(value = "topicID")int topicId) throws Exception{
        JSONObject topic = this.bbsService.getTopicDetail(topicId);
        modelMap.put("campusRecruit", topic);
    }

    @RequestMapping("/bbsReply/list")
    public void bbsReplyList(ModelMap modelMap, @RequestParam(value = "topicID")int topicId,
                             @RequestParam(value = "pageIndex")int pageIndex) throws Exception{
        List<BBSReply> bbsReplyList = this.bbsService.getReplyList(topicId, pageIndex);
        modelMap.put("campusRecruit", bbsReplyList);
    }

    @RequestMapping("/new/bbsTopic")
    public void newBBSTopic(ModelMap modelMap, @RequestParam(value = "sectionID", required = false)Integer sectionId,
                            @RequestParam(value = "companyID", required = false)Integer companyId,
                            @RequestParam(value = "userID")String userId,
                            @RequestParam(value = "title")String topicTitle,
                            @RequestParam(value = "body")String topicText) throws Exception{
        sectionId = (sectionId == null) ? 0 : sectionId;
        companyId = (companyId == null) ? 0 : companyId;
        int topicId = this.bbsService.writeNewTopic(sectionId,companyId,userId,topicTitle,topicText);
        modelMap.put("campusRecruit",topicId);
    }

    @RequestMapping("/new/bbsReply")
    public void newBBSReply(ModelMap modelMap, @RequestParam(value = "topicID")int topicId,
                            @RequestParam(value = "userID")String userId,
                            @RequestParam(value = "content")String replyContent,
                            @RequestParam(value = "bbsReplyID", required = false)Integer refReplyId) throws Exception{
        refReplyId = (refReplyId == null) ? -1 : refReplyId;
        List reply = this.bbsService.writeNewReply(topicId,userId,replyContent,refReplyId);
        modelMap.put("campusRecruit",reply);
    }

    @RequestMapping("/bbsSection/join")
    public void joinSection(ModelMap modelMap, @RequestParam(value = "userID")String userId,
                            @RequestParam(value = "companyID")int companyId) throws Exception{
        boolean join = this.bbsService.joinSection(userId, companyId);
        modelMap.put("campusRecruit",join);
    }

    @RequestMapping("/bbsSection/delete")
    public void deleteSection(ModelMap modelMap, @RequestParam(value = "userID")String userId,
                              @RequestParam(value = "sectionID")int sectionId) throws Exception{
        this.bbsService.deleteSection(userId, sectionId);
    }

}
