package com.bupt.pcncad.service.upload;

import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.User;
import com.bupt.pcncad.util.FileUtil;
import com.bupt.pcncad.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-10
 * Time: 下午5:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ResourceOperationServiceImpl implements IResourceOperationService {

    @Autowired
    private IUserDao userDao;

    public File uploadResource(MultipartFile file,String userId,StringBuilder resourceId) throws Exception {
        if (!file.isEmpty()) {
            String realName = file.getOriginalFilename();
            String type = realName.substring(realName.lastIndexOf(".") + 1);
            User user = this.userDao.findEntity("from User u where u.userId=?",userId);
            user.setImgType(type);
            this.userDao.update(user);
            resourceId.append(user.getId());
            String savePath = ResourceUtil.getRealSavePathByUserId(resourceId.toString());
            File directory = new File(savePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            InputStream from = file.getInputStream();
            File uploadFile = new File(directory, resourceId + "." + type);
            OutputStream to = new FileOutputStream(uploadFile);
            FileUtil.copy(from, to);
            return uploadFile;
        } else {
            throw new RuntimeException("文件内容不能为空");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void downloadResource(String userId, HttpServletResponse response) throws Exception {
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        if(user.getImgType() == null)
            return;
        String fileName = user.getId() + "." + user.getImgType();
        String savePath = ResourceUtil.getRealSavePathByUserId(user.getId());
        response.setContentType("application/octet-stream ");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(fileName.getBytes(), "ISO-8859-1"));
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(savePath, fileName));
            os = response.getOutputStream();
            byte[] buff = new byte[1024];
            int length = -1;
            while ((length = is.read(buff)) != 0) {
                os.write(buff, 0, length);
                os.flush();
            }
//            LoggerUtil.info(this.getClass(), WebContextThreadLocal.getCurrentUser().getUserName() + "下载:" + resource.getResourceRealName() + ":" + resource.getDownloadTimes());
        } catch (FileNotFoundException e) {
//            LoggerUtil.error(this.getClass(), "downLoadResource时没有找到下载文件" + e);
            e.printStackTrace();
        } catch (IOException e) {
//            LoggerUtil.error(this.getClass(), "IOException" + e);
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void apkDownload(HttpServletResponse response) throws Exception{

        String fileName = "campusRecruit.apk";
        Properties props = new Properties();
        props.load(ResourceUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        String savePath = (String)props.get("resourceRootPath");
        System.out.println(savePath);
        response.setContentType("application/octet-stream ");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(fileName.getBytes(), "ISO-8859-1"));
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(savePath, fileName));
            os = response.getOutputStream();
            byte[] buff = new byte[1024];
            int length = -1;
            while ((length = is.read(buff)) != 0) {
                os.write(buff, 0, length);
                os.flush();
            }
//            LoggerUtil.info(this.getClass(), WebContextThreadLocal.getCurrentUser().getUserName() + "下载:" + resource.getResourceRealName() + ":" + resource.getDownloadTimes());
        } catch (FileNotFoundException e) {
//            LoggerUtil.error(this.getClass(), "downLoadResource时没有找到下载文件" + e);
            e.printStackTrace();
        } catch (IOException e) {
//            LoggerUtil.error(this.getClass(), "IOException" + e);
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}
