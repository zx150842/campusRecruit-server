package com.bupt.pcncad.service.upload;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-10
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
public interface IResourceOperationService {
    public File uploadResource(MultipartFile file,String userId,StringBuilder resourceId) throws Exception;
    public void downloadResource(String userId, HttpServletResponse response) throws Exception;
    public void apkDownload(HttpServletResponse response) throws Exception;
}
