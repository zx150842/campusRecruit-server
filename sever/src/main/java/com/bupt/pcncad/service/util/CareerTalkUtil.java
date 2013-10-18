package com.bupt.pcncad.service.util;

import com.bupt.pcncad.dao.careerTalk.ICareerTalkDao;
import com.bupt.pcncad.dao.user.IUserDao;
import com.bupt.pcncad.domain.CareerTalk;
import com.bupt.pcncad.domain.Company;
import com.bupt.pcncad.domain.Preference;
import com.bupt.pcncad.domain.User;
import com.bupt.pcncad.util.ParseType;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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
* Time: 下午10:04
* To change this template use File | Settings | File Templates.
*/
@Component
public class CareerTalkUtil implements ICareerTalkUtil {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private ICareerTalkDao careerTalkDao;

    private String provinces = null;
    private String companyTypes = null;
    private String industrys = null;
    private String schools = null;

    private int companyType_len = 0;
    private int province_len = 0;
    private int industry_len = 0;
    private int school_len = 0;

    private int PAGE_SIZE =20;

    public List<CareerTalk> getCareerTalk(String userId, int pageIndex, int order, int famous,
                                          String r_province, String r_industry, String r_type, String school) throws Exception{
        User user = this.userDao.findEntity("from User u where u.userId=?",userId);
        final String lastReadCareerTalk = user.getUsrLastReadTalk();

        Preference preference = user.getPreference();
//        this.provinces = preference.getProvince();
//        this.companyTypes =preference.getCompanyType();
//        this.industrys = preference.getVocationSet();

        this.provinces = r_province;
        this.companyTypes = r_type;
        this.industrys = r_industry;
        this.schools = school;

        final String[] arr_companyType = companyTypes.split(",");
        final String[] arr_province = provinces.split(",");
        final String[] arr_industry = industrys.split(",");
        final String[] arr_school = schools.split(",");

        this.companyType_len = arr_companyType.length;
        this.province_len = arr_province.length;
        this.industry_len = arr_industry.length;
        this.school_len = arr_school.length;

        for(int i=0; i<school_len; i++){
            if(arr_school[i].equals("北邮"))
                arr_school[i] = "北京邮电大学";
            else if(arr_school[i].equals("北师"))
                arr_school[i] = "北京师范大学";
            else if(arr_school[i].equals("北大"))
                arr_school[i] = "北京大学";
            else if(arr_school[i].equals("清华"))
                arr_school[i] = "清华大学";
            else if(arr_school[i].equals("北航"))
                arr_school[i] = "北京航空航天大学";
            else if(arr_school[i].equals("北理"))
                arr_school[i].equals("北京理工大学");
        }

        final String sql = this.getCareerTalkSql(pageIndex, order, famous, school);
        final int pageNo = pageIndex;
        final int type_len = companyType_len;
        final int pro_len = province_len;
        final int ind_len = industry_len;
        final int sch_len = school_len;
        int type = 0;
        if(pageIndex >= 0)
            type = 1;
        final int f_type = type;
        final String school_name = school;

        List<CareerTalk> careerTalkList = this.careerTalkDao.getByCustom(new HibernateCallback<List<CareerTalk>>() {
            @Override
            public List<CareerTalk> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql).addEntity(CareerTalk.class);
                if(f_type == 0) {
                    query.setString(0,lastReadCareerTalk);
                    for(int i=0; i < type_len; i++){
                        query.setString(i+1,arr_companyType[i]);
                    }
                    for(int i=0; i < pro_len; i++){
                        query.setString(i+type_len+1,arr_province[i]);
                    }
                }
                else {
                    for(int i=0; i < type_len; i++){
                        query.setString(i,arr_companyType[i]);
                    }
                    for(int i=0; i < pro_len; i++){
                        query.setString(i+type_len,arr_province[i]);
                    }
                    for(int i=0; i< ind_len; i++){
                        query.setString(i+type_len+pro_len,arr_industry[i]);
                    }

                    for(int i=0; i< sch_len; i++) {
                        query.setString(i + type_len + pro_len + ind_len, arr_school[i]);
                    }
                    query.setInteger(type_len + pro_len + ind_len + sch_len, pageNo*PAGE_SIZE);

                }
                List pusList = query.list();
                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        if(careerTalkList.size() > 0){
            for(int i=0; i<careerTalkList.size(); i++){
                if(user.getUsrLastReadTalk() == null){
                    user.setUsrLastReadTalk(careerTalkList.get(i).getDate());
                    this.userDao.update(user);
                }
                else{
                    int result=user.getUsrLastReadTalk().compareTo(careerTalkList.get(i).getDate());
                    if(result < 0){
                        user.setUsrLastReadTalk(careerTalkList.get(i).getDate());
                        this.userDao.update(user);
                    }
                }
            }
        }

        return careerTalkList;
    }

    private String getCareerTalkSql(int pageIndex, int order, int famous, String school) throws Exception{       //pageIndex=-1 为消息推送 否则为list
        StringBuffer sb = new StringBuffer();
        String temp_sql = "select * from CAREER_TALK career, COMPANY com where career.company_id=com.id and ";
        sb.append(temp_sql);
        if(pageIndex == -1)
            sb.append("str_to_date(career.career_talk_date,'%Y-%m-%d %H:%i:%s')>str_to_date(?,'%Y-%m-%d %H:%i:%s')");
        else {
            sb.append("str_to_date(career.career_talk_date,'%Y-%m-%d')>=CURDATE() ");
            if(famous == 1)
                sb.append("and com.famous_company=1");
        }
        if(!companyTypes.equals("0")){
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
        if(!provinces.equals("0")){
            for(int i=0; i < province_len; i++){
                if(i == 0)
                    sb.append(" and (find_in_set(?,com.province)");
                else
                    sb.append(" or find_in_set(?,com.province)");
                if(i == province_len-1)
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
        if(pageIndex == -1)
            sb.append(" order by career.career_talk_date asc");
        else {
            if(!school.equals("全部")) {
                for(int i=0; i < school_len; i++){
                    if(i == 0)
                        sb.append(" and (career.school_name=?");
                    else
                        sb.append(" or career.school_name=?");
                    if(i == school_len-1)
                        sb.append(")");
                }
            }
            else
                school_len = 0;
            switch (order){
                case 0:
                    sb.append(" order by career.career_talk_date asc limit ?,20");   //按发布时间排序
                    break;
                case 1:
                    sb.append(" order by career.id desc limit ?,20");   //按录入时间排序
                    break;
                case 2:
                    sb.append(" order by career.joins desc limit ?,20");   //按joins
                    break;
                case 3:
                    sb.append(" order by career.replies desc limit ?,20"); //按replies
                    break;
                case 4:
                    sb.append(" order by career.clicks desc limit ?,20");   //按clicks
            }

        }
        final String sql = sb.toString();
        return sql;
    }

    public JSONObject parse(CareerTalk careerTalk){
        Company company = careerTalk.getCompany();
        Map map = new HashMap();
        if(careerTalk.getSchoolName() == null)
            map.put("schoolName","");
        else
            map.put("schoolName",careerTalk.getSchoolName());
        if(careerTalk.getPlace() == null)
            map.put("place","");
        else
            map.put("place",careerTalk.getPlace());
        map.put("joins",careerTalk.getJoins());
        map.put("companyName",careerTalk.getCompanyName());
        map.put("clicks",careerTalk.getClicks());
        map.put("type", ParseType.parse2type(company.getType()));
        map.put("topicID",careerTalk.getTopicID());
        if(careerTalk.getDate() == null)
            map.put("date","");
        else
            map.put("date",careerTalk.getDate());
        if(careerTalk.getTime() == null)
            map.put("time","");
        else
            map.put("time",careerTalk.getTime());
        map.put("replies",careerTalk.getReplies());
        map.put("id",careerTalk.getId());
        map.put("companyID",careerTalk.getCompany().getId());
        map.put("sourceFrom",careerTalk.getSourceFrom());
        if(careerTalk.getUrl() == null)
            map.put("url","");
        else
            map.put("url",careerTalk.getUrl());
        map.put("famous",careerTalk.getCompany().getFamousCompany());
        JSONObject fromObject = JSONObject.fromObject(map);
        return fromObject;
    }
}
