package com.bupt.pcncad.service.source;

import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-4
 * Time: 下午3:35
 * To change this template use File | Settings | File Templates.
 */
public interface ISourceService {
    public JSONObject getSource() throws Exception;
    public void setSource(int sourceId, int db) throws Exception;
    public JSONObject getCTSource() throws Exception;
    public void setCTSource(int ctSourceId, int db) throws Exception;
}
