package com.bupt.pcncad.service.company;

import com.bupt.pcncad.dao.bbssection.IBBSSectionDao;
import com.bupt.pcncad.dao.careerTalk.ICareerTalkDao;
import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.recruit.IRecruitDao;
import com.bupt.pcncad.domain.BBSSection;
import com.bupt.pcncad.domain.CareerTalk;
import com.bupt.pcncad.domain.Company;
import com.bupt.pcncad.domain.Job;
import com.bupt.pcncad.service.util.ICareerTalkUtil;
import com.bupt.pcncad.service.util.IRecruitUtil;
import com.bupt.pcncad.util.ParseIndustry;
import com.bupt.pcncad.util.ParseProvince;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-25
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private IBBSSectionDao sectionDao;
    @Autowired
    private ICareerTalkDao careerTalkDao;
    @Autowired
    private IRecruitDao recruitDao;
    @Autowired
    private ICareerTalkUtil careerTalkUtil;
    @Autowired
    private IRecruitUtil recruitUtil;

    public int setCompany(String companyName,int type,String province,String info,String industry,
                          String state,String statetime,int famous, int pageType) throws Exception{
        if(companyName == null)
            return 0;
        Company company = this.companyDao.findEntity("from Company c where c.companyName=?", companyName);
        if(company == null){
            company = new Company();
            company.setCompanyName(companyName);
            company.setInputTime(new Date());
            if(pageType == 1){
//                company.setType(2);
//                company.setPlace(ParseProvince.parse("全国"));
//                company.setIndustry("1,2,3,4");
            }
            this.companyDao.save(company);
            BBSSection section = new BBSSection();
            section.setCompanyID(company.getId());
            section.setSectionName(companyName);
            this.sectionDao.save(section);
            company.setSectionID(section.getId());
            this.companyDao.update(company);
        }

        if(type > 0)
            company.setType(type);
        else
            company.setType(2);
        if(province != null) {
            if(!province.equals(company.getPlace())) {
                if(company.getPlace() != null && !ParseProvince.parse2province(company.getPlace()).equals("全国") && !province.equals(ParseProvince.parse("全国")))
                    company.setPlace(company.getPlace() + "," + province);
                else
                    company.setPlace(province);
            }
        }
        else if(pageType != 1)
            company.setPlace(ParseProvince.parse("全国"));
        if(info != null)
            company.setIntroduction(info);
        if(industry != null) {
            if(!industry.equals(company.getIndustry())) {
                if(company.getIndustry() != null)
                    company.setIndustry(company.getIndustry() + "," +industry);
                else
                    company.setIndustry(industry);
            }
        }
        else if(pageType != 1)
            company.setIndustry("1,2,3,4");
        if(state != null)
            company.setState(state);
        if(statetime != null)
            company.setStatetime(statetime);
        if(famous == 1)
            company.setFamousCompany(famous);
        this.companyDao.update(company);

        return company.getId();
    }

    public List<Company> getCompanyList() throws Exception{
        List<Company> companyList = this.companyDao.findAll();
        return companyList;
    }

    public JSONObject getCompany(int companyId) throws Exception{
        Company company = this.companyDao.findEntity("from Company com where com.id=?",companyId);
        ParseProvince parseProvince = new ParseProvince();
        Map map = new HashMap();
        if(company.getIntroduction() == null)
            map.put("introduction","");
        else
            map.put("introduction",company.getIntroduction());
        if(company.getPlace() == null)
            map.put("place","");
        else
            map.put("place",parseProvince.parse2province(company.getPlace()));
        map.put("companyName",company.getCompanyName());
        map.put("type", company.getType());
        if(company.getIndustry() == null)
            map.put("industry","");
        else
            map.put("industry", ParseIndustry.parse2industry(company.getIndustry()));
        map.put("famous",company.getFamousCompany());
        if(company.getState() == null)
            map.put("company_state","");
        else
            map.put("company_state",company.getState());
        if(company.getStatetime() == null)
            map.put("statetime","");
        else
            map.put("statetime",company.getStatetime());
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }

    public List searchCompany(String searchKey, int type) throws Exception{      //1 careerTalk 2 recruit 0 company

        if(type > 0)
            searchKey = new String(searchKey.getBytes("ISO-8859-1"), "UTF-8");
        final String key = searchKey;
        final String sql = "select * from COMPANY c where c.company_name like ?";
        List<Company> companyList = this.companyDao.getByCustom(new HibernateCallback<List<Company>>() {
            @Override
            public List<Company> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(Company.class);
                query.setString(0,"%"+key+"%");
                List pusList = query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        List list = new ArrayList();
        for(int i=0; i<companyList.size(); i++){
            Company company = companyList.get(i);
            if(type == 1){
                List<CareerTalk> careerTalkList = this.careerTalkDao.find("from CareerTalk c where c.company.id=?",company.getId());
                for(int j=0; j<careerTalkList.size(); j++){
                    CareerTalk careerTalk = careerTalkList.get(j);
                    JSONObject fromObject = this.careerTalkUtil.parse(careerTalk);
                    list.add(fromObject);
                }
            }
            else if(type == 2){
                List<Job> jobList = this.recruitDao.find("from Job job where job.company.id=?",company.getId());
                for(int j=0; j<jobList.size(); j++){
                    Job job = jobList.get(j);
                    JSONObject fromObject = this.recruitUtil.parse(job);
                    list.add(fromObject);
                }
            }
            else{
                return companyList;
            }
        }
        return list;
    }
}
