package com.bupt.pcncad.service.search;

import com.bupt.pcncad.dao.careerTalk.ICareerTalkDao;
import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.recruit.IRecruitDao;
import com.bupt.pcncad.domain.CareerTalk;
import com.bupt.pcncad.domain.Company;
import com.bupt.pcncad.domain.Job;
import com.bupt.pcncad.service.util.ICareerTalkUtil;
import com.bupt.pcncad.service.util.IRecruitUtil;
import com.bupt.pcncad.util.ParseProvince;
import jeasy.analysis.MMAnalyzer;
import net.sf.json.JSONObject;
import org.compass.core.CompassHits;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-20
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CompanySearchServiceImpl implements ICompanySearchService {

    @Autowired
    private CompassTemplate compassTemplate;
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private ICareerTalkDao careerTalkDao;
    @Autowired
    private ICareerTalkUtil careerTalkUtil;
    @Autowired
    private IRecruitDao recruitDao;
    @Autowired
    private IRecruitUtil recruitUtil;

    public List search(String queryString, int type) throws Exception{     //1 careerTalk 2 recruit 0 company
        List list=new ArrayList();
        String key = queryString;
        if(type > 0)
            key = new String(key.getBytes("ISO-8859-1"), "UTF-8");
        String[] keys = null;
        MMAnalyzer analyzer = new MMAnalyzer();
        try{
            System.out.println(analyzer.segment(queryString, " "));
            key = analyzer.segment(key, ",");
            keys = key.split(",");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        CompassSession session=compassTemplate.getCompass().openSession();
        CompassQueryBuilder queryBuilder = session.queryBuilder();
        CompassQueryBuilder.CompassBooleanQueryBuilder booleanQueryBuilder = queryBuilder.bool();
        for(int i=0; i<keys.length; i++){
            booleanQueryBuilder.addShould(queryBuilder.queryString(keys[i]).toQuery());
        }
        CompassHits hits = booleanQueryBuilder.toQuery().hits();
//        CompassHits hits = queryBuilder.bool().addShould(queryBuilder.queryString("京东").toQuery()).
//                addShould(queryBuilder.queryString("淘").toQuery()).toQuery().hits();
//        CompassHits hits= session.queryBuilder().queryString(key).toQuery().hits();

        System.out.println("搜索词:"+key);
        System.out.println("命中数:"+hits.length());
        for (int i = 0; i < hits.length(); i++) {
            if(i > 20)
                break;
            String name=hits.highlighter(i).fragment("companyName");
            if(type > 0){
                Object object = hits.data(i);
                if(object instanceof Company) {
                    Company company = (Company) object;
                    int id = company.getId();
                    if(type == 1) {
                        List<CareerTalk> careerTalkList = this.careerTalkDao.find("from CareerTalk c where c.company.id=?",company.getId());
                        for(int j=0; j<careerTalkList.size(); j++){
                            CareerTalk careerTalk = careerTalkList.get(j);
                            JSONObject fromObject = this.careerTalkUtil.parse(careerTalk);
                            list.add(fromObject);
                        }
                    }
                    else if(type == 2){
                        List<Job> jobList = this.recruitDao.find("from Job job where job.company.id=? and job.deleteFlag=0",company.getId());
                        for(int j=0; j<jobList.size(); j++){
                            Job job = jobList.get(j);
                            JSONObject fromObject = this.recruitUtil.parse(job);
                            list.add(fromObject);
                        }
                    }
                }
            }
            else if(type == 0) {
                Map map = new HashMap();
                map.put("name",name);
                Company company = (Company)hits.data(i);
                map.put("id",company.getId());
                JSONObject jsonObject = JSONObject.fromObject(map);
                list.add(jsonObject);
            }
        }
        return list;
    }

    public boolean rebuildIndex() throws Exception{
        List<Company> companyList = this.companyDao.findAll();
        for(int i=0; i<companyList.size(); i++){
            compassTemplate.delete(companyList.get(i));
            compassTemplate.create(companyList.get(i));
        }
        return true;
    }

    final int MAXNUM = 1000;
    final String[] stop_word = {"公司","科技","有限公司","集团","中国","网","股份有限公司","软件","研究所","技术",
            "技","研究院","信息","网络","中心","工业","汽车","工程","设计","银行","分行","数码","研发","中国工商银行",
            "份","游戏","品","设计","中科院","汽","有限责任公司","咨询","电子","分公司","集团公司","电子科","通信",
            "招聘","2014","校园","校园招聘","2013","武汉","专场","文化","互动","计算机","合","东北","青岛","深圳市","设备",
            "台","江苏省","美国","企业","控股","烟台","教育","联合","发展"};
    public List findBestSearch(String queryString) throws Exception{

        List list=new ArrayList();
        String key = queryString;
        String[] keys = null;
        MMAnalyzer analyzer = new MMAnalyzer();
        try{
            System.out.println(analyzer.segment(queryString, " "));
            key = analyzer.segment(key, ",");
            keys = key.split(",");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        List<String> useful_key = new ArrayList<String>();
        for(int i=0; i<keys.length; i++){
            int is_userful = 1;
            for(int j=0; j<stop_word.length; j++){
                if(keys[i].equals(stop_word[j])) {
                    is_userful = 0;
                    break;
                }
            }
            if(is_userful == 1)
                useful_key.add(keys[i]);
        }

        CompassSession session=compassTemplate.getCompass().openSession();
        CompassQueryBuilder queryBuilder = session.queryBuilder();
        Map map = new HashMap();
        int min_search_count = MAXNUM;
        String min_search_key = null;
        CompassHits min_search_hits = null;
        for(int i=0; i<useful_key.size(); i++){
            CompassQueryBuilder.CompassBooleanQueryBuilder booleanQueryBuilder = queryBuilder.bool();
            booleanQueryBuilder.addShould(queryBuilder.queryString(useful_key.get(i)).toQuery());
            CompassHits hits = booleanQueryBuilder.toQuery().hits();
            map.put(useful_key.get(i), hits.length());
            if((hits.length() < min_search_count)/* && (hits.length() > 0)*/) {
                min_search_count = hits.length();
                min_search_key = useful_key.get(i);
                min_search_hits = hits;
            }
        }
        if(min_search_count > 3)
            return null;
        else {
            for (int i = 0; i < min_search_hits.length(); i++) {
                Object object = min_search_hits.data(i);
                String name=min_search_hits.highlighter(i).fragment("companyName");
                if(object instanceof Company) {
                    Company company = (Company) object;
                    System.out.println(company.getCompanyName());
                    Map map1 = new HashMap();
                    map1.put("name",name);
                    map1.put("id",company.getId());
                    JSONObject jsonObject = JSONObject.fromObject(map1);
                    list.add(jsonObject);
                }
            }
        }
        return list;
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String search_key = (String)entry.getKey();
//            int search_count = (Integer)entry.getValue();
//        }
    }

    public int findResult(String queryString) throws Exception{

        List list=new ArrayList();
        String key = queryString;
        String[] keys = null;
        MMAnalyzer analyzer = new MMAnalyzer();
        try{
//            System.out.println(analyzer.segment(queryString, " "));
            key = analyzer.segment(key, ",");
            keys = key.split(",");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        List<String> useful_key = new ArrayList<String>();
        for(int i=0; i<keys.length; i++){
            int is_userful = 1;
            for(int j=0; j<stop_word.length; j++){
                if(keys[i].equals(stop_word[j])) {
                    is_userful = 0;
                    break;
                }
            }
            if(is_userful == 1)
                useful_key.add(keys[i]);
        }

        String operation = ParseProvince.judgeOperator(useful_key);
        if(operation != null) {
            useful_key = new ArrayList<String>();
            String[] operations = operation.split(",");
            useful_key.add(operations[0] + operations[1]);
            useful_key.add(operations[1] + operations[0]);
        }


        CompassSession session=compassTemplate.getCompass().openSession();
        CompassQueryBuilder queryBuilder = session.queryBuilder();
        Map map = new HashMap();
        int min_search_count = MAXNUM;
        String min_search_key = null;
        CompassHits min_search_hits = null;
        for(int i=0; i<useful_key.size(); i++){
            CompassQueryBuilder.CompassBooleanQueryBuilder booleanQueryBuilder = queryBuilder.bool();
            System.out.println(useful_key.get(i));
            booleanQueryBuilder.addShould(queryBuilder.queryString(useful_key.get(i)).toQuery());
            CompassHits hits = booleanQueryBuilder.toQuery().hits();
            map.put(useful_key.get(i), hits.length());
            if((hits.length() < min_search_count) && (hits.length() > 0)) {
                min_search_count = hits.length();
                min_search_key = useful_key.get(i);
                min_search_hits = hits;
            }
        }
        if(min_search_count > 3)
            return 0;
        else {
            for (int i = 0; i < min_search_hits.length(); i++) {
                Object object = min_search_hits.data(i);
                String name=min_search_hits.highlighter(i).fragment("companyName");
                if(object instanceof Company) {
                    Company company = (Company) object;
//                    System.out.println(company.getCompanyName());
                    return company.getId();

                }
            }
        }
        return 0;
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String search_key = (String)entry.getKey();
//            int search_count = (Integer)entry.getValue();
//        }
    }

    public String divideWord(String queryString) throws Exception{
        final String[] stop_word = {
                "招聘","2014","校园","校园招聘","2013","专场","暑期","实习"};
        String key = queryString;
        String[] keys = null;
        MMAnalyzer analyzer = new MMAnalyzer();
        try{
//            System.out.println(analyzer.segment(queryString, " "));
            key = analyzer.segment(key, ",");
            keys = key.split(",");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        for(int i=0; i<stop_word.length; i++){
            queryString = queryString.replace(stop_word[i],"");
        }
        return queryString;
//        String useful_key = null;
//        for(int i=0; i<keys.length; i++){
//            int is_userful = 1;
//            for(int j=0; j<stop_word.length; j++){
//                if(keys[i].equals(stop_word[j])) {
//                    is_userful = 0;
//                    break;
//                }
//            }
//            if(is_userful == 1) {
//                if(useful_key == null)
//                    useful_key = keys[i];
//                else
//                    useful_key = useful_key/* + " "*/ + keys[i];
//            }
//
//        }
//        return useful_key;
    }

    public void test() throws Exception{
        List<Company> companyList = this.companyDao.findAll();
        MMAnalyzer analyzer = new MMAnalyzer();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i=0; i<companyList.size(); i++){
            String companyName = companyList.get(i).getCompanyName();
            String key = analyzer.segment(companyName, ",");
            String[] keys = key.split(",");
            for(int j=0; j<keys.length; j++){
                Integer count = map.get(keys[j]);
                if (count == null) {
                    map.put(keys[j], 1);
                }
                else {
                    map.put(keys[j], count + 1);
                }
            }
        }
        List<Map.Entry<String, Integer>> list_Data = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
            {
                if ((o2.getValue() - o1.getValue())>0)
                    return 1;
                else if((o2.getValue() - o1.getValue())==0)
                    return 0;
                else
                    return -1;
            }
        });
        for(int i=0; i<list_Data.size(); i++){
            System.out.println("key:"+list_Data.get(i).getKey()+"------value:"+list_Data.get(i).getValue());
        }

    }

    public void setCompassTemplate(CompassTemplate compassTemplate) {
        this.compassTemplate = compassTemplate;
    }

    public CompassTemplate getCompassTemplate() {
        return compassTemplate;
    }


}
