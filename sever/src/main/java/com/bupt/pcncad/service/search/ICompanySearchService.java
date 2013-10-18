package com.bupt.pcncad.service.search;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-20
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public interface ICompanySearchService {
    public List search(String queryString, int type) throws Exception;
    public boolean rebuildIndex() throws Exception;
    public List findBestSearch(String queryString) throws Exception;
    public void test() throws Exception;
    public int findResult(String queryString) throws Exception;
    public String divideWord(String queryString) throws Exception;
}
