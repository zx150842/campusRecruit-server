package com.bupt.pcncad.controller.upload;

import com.bupt.pcncad.service.upload.IResourceOperationService;
import com.bupt.pcncad.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-10
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mobile/user")
public class ResourceOperationController {
    @Autowired
    private IResourceOperationService resourceOperationService;
    @Autowired
    private IUserService userService;

    @RequestMapping("/portrait/update")
    public void upload(@RequestParam("portrait")MultipartFile file, ModelMap modelMap,
                       @RequestParam("userID")String userID)throws Exception{
        try {
            if ("".equals(userID) || null == userID) {
                return ;
            }

            StringBuilder resourceId=new StringBuilder();
            this.resourceOperationService.uploadResource(file,userID,resourceId);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping("/portrait/download")
    public void download(@RequestParam(value = "userID", required = true) String userId, HttpServletResponse response) throws Exception {
        this.resourceOperationService.downloadResource(userId, response);
    }

    @RequestMapping("/apk/download")
    public void apkDownload(HttpServletResponse response) throws Exception{
        this.resourceOperationService.apkDownload(response);
    }
}
