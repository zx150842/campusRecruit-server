package com.bupt.pcncad.service.notice;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 下午10:46
 * To change this template use File | Settings | File Templates.
 */
public interface INoticeService {
    public List getNotice(String userId) throws Exception;
}
