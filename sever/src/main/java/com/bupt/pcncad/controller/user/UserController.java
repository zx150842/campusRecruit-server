package com.bupt.pcncad.controller.user;

import com.bupt.pcncad.domain.Preference;
import com.bupt.pcncad.service.upload.IResourceOperationService;
import com.bupt.pcncad.service.user.IUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 下午6:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IResourceOperationService resourceOperationService;

    @RequestMapping("/login")
    public void login(ModelMap modelMap, @RequestParam(value = "userName")String userId,
                      @RequestParam(value = "pwd")String userPasswd) throws Exception{
        JSONObject loginCheck = this.userService.loginCheck(userId,userPasswd);
        modelMap.put("campusRecruit",loginCheck);
    }

    @RequestMapping("/register")
    public void register(ModelMap modelMap,@RequestParam(value = "userID")String userId,
                         @RequestParam(value = "userName")String userAccount,
                         @RequestParam(value = "pwd")String userPasswd) throws Exception{
        String register = this.userService.registerUser(userId, userAccount, userPasswd);
        modelMap.put("campusRecruit", register);
    }

    @RequestMapping("/preference/get")
    public void preference(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        Preference preference = this.userService.getUserPreference(userId);
        modelMap.put("campusRecruit", preference);
    }

    @RequestMapping("/preference/set")
    public void setPreferenceNotify(ModelMap modelMap, @RequestParam(value = "userID")String userId,
                                    @RequestParam(value = "province",required = false)String province,
                                    @RequestParam(value = "type", required = false)String companyType,
                                    @RequestParam(value = "industry",required = false)String industry,
                                    @RequestParam(value = "notifyType", required = false)String notifyType,
                                    @RequestParam(value = "source", required = false)String sourceFrom) throws Exception{
        boolean setNotify = this.userService.setUserPreference(userId,province,industry,companyType,notifyType,sourceFrom);
        modelMap.put("campusRecruit", setNotify);
    }

    @RequestMapping("/get")
    public void getUser(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        JSONObject user = this.userService.getUser(userId);
        modelMap.put("campusRecruit",user);
    }

    @RequestMapping("/set")
    public void setUser(ModelMap modelMap, @RequestParam(value = "userID")String userId,
                        @RequestParam(value = "gender")int gender,
                        @RequestParam(value = "school")String schoolName,
                        @RequestParam(value = "major")String major,
                        @RequestParam(value = "email")String email) throws Exception{
        this.userService.setUser(userId,gender,schoolName,major,email);
    }

    @RequestMapping("/replies")
    public void getReplies(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        List list = this.userService.getReplies(userId);
        modelMap.put("campusRecruit", list);
    }

    @RequestMapping("/detail")
    public void getDetail(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        JSONObject user = this.userService.getDetail(userId);
        modelMap.put("campusRecruit",user);
    }

    @RequestMapping("/comments")
    public void getComment(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        List list = this.userService.getAllComment(userId);
        modelMap.put("campusRecruit",list);
    }

    @RequestMapping("/message/new")
    public void sendLetter(ModelMap modelMap, @RequestParam(value = "userID")String fromUserId,
                           @RequestParam(value = "toUserID")String toUserId,
                           @RequestParam(value = "content")String content) throws Exception{
        int id = this.userService.writeLetter(fromUserId,toUserId,content);
        modelMap.put("campusRecruit",id);
    }

    @RequestMapping("/message/list")
    public void getLetter(ModelMap modelMap, @RequestParam(value = "userID")String userId,
                          @RequestParam("otherUserID")String otherUserId,
                          @RequestParam(value = "createdTime", required = false)String createdTime) throws Exception{
        if(createdTime == null)
            createdTime = "0000-00-00 00:00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = format.parse(createdTime);
        List<JSONObject> list = this.userService.getLetter(userId, otherUserId,createdTime);
        modelMap.put("campusRecruit",list);
    }

    @RequestMapping("/message/contacts")
    public void getAllLetters(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        List list = this.userService.getUserLetters(userId);
        modelMap.put("campusRecruit",list);
    }

    @RequestMapping("/topics")
    public void getUserTopics(ModelMap modelMap, @RequestParam(value = "userID")String userId) throws Exception{
        List list = this.userService.getUserTopics(userId);
        modelMap.put("campusRecruit",list);
    }
}
