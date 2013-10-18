package com.bupt.pcncad.service.util;

import com.bupt.pcncad.dao.recruit.IRecruitDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.Company;
import com.bupt.pcncad.domain.Job;
import com.bupt.pcncad.domain.Preference;
import com.bupt.pcncad.domain.User;
import com.bupt.pcncad.util.ParseIndustry;
import com.bupt.pcncad.util.ParseProvince;
import com.bupt.pcncad.util.ParseType;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-17
 * Time: 下午11:35
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RecruitUtil implements IRecruitUtil {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRecruitDao recruitDao;

    private String provinces = null;
    private String companyTypes = null;
    private String industrys = null;
    private String sources = null;

    private int companyType_len = 0;
    private int province_len = 0;
    private int industry_len = 0;
    private int source_len = 0;
    private int PAGE_SIZE =20;

    public List<Job> getRecruit(String userId, int pageIndex, int order, int famous,
                                String r_province, String r_industry, String r_type, String r_source) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        final String lastReadRecruit = user.getUsrLastReadRecruit();

        Preference preference = user.getPreference();
//        this.provinces = preference.getProvince();
//        this.companyTypes =preference.getCompanyType();
//        this.industrys = preference.getVocationSet();

        this.provinces = r_province;
        this.companyTypes = r_type;
        this.industrys = r_industry;
        this.sources = r_source;

        final String[] arr_companyType = companyTypes.split(",");
        final String[] arr_province = provinces.split(",");
        final String[] arr_industry = industrys.split(",");
        final String[] arr_source = sources.split(",");

        this.companyType_len = arr_companyType.length;
        this.province_len = arr_province.length;
        this.industry_len = arr_industry.length;
        this.source_len = arr_source.length;

        final String sql = this.getRecruitSql(pageIndex, order, famous);
        final int pageNo = pageIndex;
        final int type_len = companyType_len;
        final int pro_len = province_len;
        final int ind_len = industry_len;
        final int sou_len = source_len;
        int type = 0;
        if(pageIndex >= 0)
            type = 1;
        final int f_type = type;

        List<Job> jobs = this.recruitDao.getByCustom(new HibernateCallback<List<Job>>() {
            @Override
            public List<Job> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(Job.class);
                if(f_type == 0) {
                    query.setString(0,lastReadRecruit);
                    for(int i=0; i < type_len; i++){
                        query.setString(i+1,arr_companyType[i]);
                    }
                    for(int i=0; i < pro_len; i++){
                        query.setString(i+type_len+1,arr_province[i]);
                    }
                    for(int i=0; i < ind_len; i++){
                        query.setString(i+type_len+pro_len+1,arr_industry[i]);
                    }
                }
                else {
                    for(int i=0; i < type_len; i++){
                        query.setString(i,arr_companyType[i]);
                    }
                    for(int i=0; i < pro_len; i++){
                        query.setString(i+type_len,arr_province[i]);
                    }
                    for(int i=0; i < ind_len; i++){
                        query.setString(i+type_len+pro_len,arr_industry[i]);
                    }
                    for(int i=0; i<sou_len; i++){
                        query.setString(i+type_len+pro_len+ind_len,arr_source[i]);
                    }
                    query.setInteger(type_len + pro_len + ind_len + sou_len, pageNo*PAGE_SIZE);
                }

                List pusList = query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        if(jobs.size() > 0){
            for(int i=0; i<jobs.size(); i++){
                if(user.getUsrLastReadRecruit() == null){
                    user.setUsrLastReadRecruit(jobs.get(i).getCreatedTime());
                    this.userDao.update(user);
                }
                else{
                    int result = user.getUsrLastReadRecruit().compareTo(jobs.get(i).getCreatedTime());
                    if(result < 0){
                        user.setUsrLastReadRecruit(jobs.get(i).getCreatedTime());
                        this.userDao.update(user);
                    }
                }
            }

        }

        return jobs;
    }

    private String getRecruitSql(int pageIndex, int order, int famous) throws Exception{
        String temp_sql = "select * from JOB job, COMPANY com where job.company_id=com.id and job.delete_flag=0";
        StringBuilder sb = new StringBuilder();
        sb.append(temp_sql);
        if(pageIndex == -1)
            sb.append(" and str_to_date(job.created_time,'%Y-%m-%d %H:%i:%s')>str_to_date(?,'%Y-%m-%d %H:%i:%s')");
        else {
            if(famous == 1)
                sb.append(" and com.famous_company=1");
        }
        if(!companyTypes.equals("0")) {
            for(int i=0; i < companyType_len; i++){
                if(i == 0)
                    sb.append(" and (find_in_set(?,com.company_type)");
                else
                    sb.append(" or find_in_set(?,com.company_type)");
                if(i == companyType_len-1)
                    sb.append(")");
            }
        }
        else
            companyType_len = 0;
        if(!provinces.equals("0")) {
            for(int i=0; i < province_len; i++){
                if(i == 0)
                    sb.append(" and (find_in_set(?,job.place)");
                else
                    sb.append(" or find_in_set(?,job.place)");
                if(i == province_len - 1)
                    sb.append(")");
            }
        }
        else
            province_len = 0;
        if(!industrys.equals("0")) {
            for(int i=0; i < industry_len; i++){
                if(i == 0)
                    sb.append(" and (find_in_set(?,com.industry)");
                else
                    sb.append(" or find_in_set(?,com.industry)");
                if(i == industry_len - 1)
                    sb.append(")");
            }
        }
        else
            industry_len = 0;
        if(!sources.equals("0")) {
            for(int i=0; i < source_len; i++){
                if(i == 0)
                    sb.append(" and (find_in_set(?,job.source_from)");
                else
                    sb.append(" or find_in_set(?,job.source_from)");
                if(i == source_len-1)
                    sb.append(")");
            }
        }
        else
            source_len = 0;


        if(pageIndex == -1)
            sb.append(" order by job.created_time desc");
        else {
            switch (order){
                case 0:
                    sb.append(" order by job.created_time desc limit ?,20");      //默认排序
                    break;
                case 1:
                    sb.append(" order by job.joins desc limit ?,20");  //按joins
                    break;
                case 2:
                    sb.append(" order by job.replies desc limit ?,20"); //按replies
                    break;
                case 3:
                    sb.append(" order by job.clicks desc limit ?,20");   //按clicks
                    break;
            }
        }
        final String sql = sb.toString();
        return sql;
    }

    public JSONObject parse(Job job) throws Exception{
        Company company = job.getCompany();
        Map map = new HashMap();
        map.put("createdTime",job.getCreatedTime());
        map.put("position",job.getPosition());
        map.put("companyName",job.getCompanyName());
        map.put("type", ParseType.parse2type(company.getType()));
        map.put("topicID",job.getTopicID());
        map.put("clicks",job.getClicks());
        ParseProvince parseProvince = new ParseProvince();
        map.put("place",parseProvince.parse2province(job.getPlace()));
        map.put("joins",job.getJoins());
        map.put("replies",job.getReplies());
        map.put("id",job.getId());
        map.put("companyID",job.getCompany().getId());
        if(company.getIndustry() == null)
            map.put("industry","");
        else
            map.put("industry", ParseIndustry.parse2industry(company.getIndustry()));
        map.put("famous",company.getFamousCompany());
        String sourceFrom = null;
        if(job.getSourceFrom().equals("1"))
            sourceFrom = "大街网";
        else if(job.getSourceFrom().equals("2"))
            sourceFrom = "北邮人";
        else if(job.getSourceFrom().equals("3"))
            sourceFrom = "水木";

        map.put("sourceFrom", sourceFrom);
        map.put("url",job.getUrl());
        if(job.getForm() == null)
            map.put("form",0);
        else
            map.put("form",1);
        if(job.getImgUrl() == null)
            map.put("img",0);
        else
            map.put("img",1);
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }
}
