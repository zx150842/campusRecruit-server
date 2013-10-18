package com.bupt.pcncad.service.company;

import com.bupt.pcncad.domain.Company;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-25
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
public interface ICompanyService {
    public int setCompany(String companyName,int type,String province,String info,String industry,
                          String state,String statetime, int famous, int pageType) throws Exception;
    public List<Company> getCompanyList() throws Exception;
    public JSONObject getCompany(int companyId) throws Exception;
    public List searchCompany(String searchKey, int type) throws Exception;
}
