package com.bupt.pcncad;

import com.bupt.pcncad.dao.bbssection.IBBSSectionDao;
import com.bupt.pcncad.dao.bbstopic.IBBSTopicDao;
import com.bupt.pcncad.dao.company.ICompanyDao;
import com.bupt.pcncad.dao.recruit.IRecruitDao;
import com.bupt.pcncad.dao.source.IBYRSourceDao;
import com.bupt.pcncad.dao.source.IShuiMuDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-22
 * Time: 上午4:22
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml","/spring/applicationContext-db.xml"})
public class TestDao {
    @Autowired
    private ICompanyDao companyDao;
    @Autowired
    private IBYRSourceDao byrSourceDao;
    @Autowired
    private IShuiMuDao shuiMuDao;
    @Autowired
    private IRecruitDao recruitDao;
    @Autowired
    private IBBSSectionDao sectionDao;
    @Autowired
    private IBBSTopicDao topicDao;

    private static final String ACADEMY_FILE_URL = "data" + File.separator + "academy" + File.separator + "academy.txt";

    private static final String TEACHER_FILE_URL = "data" + File.separator + "user" + File.separator + "teacher.txt";

    private static final String STUDENT_FILE_URL = "data" + File.separator + "user" + File.separator + "student.txt";

    private static final String EMAIL = "data" + File.separator + "email" + File.separator + "email.txt";

    @Test
    public void test() {
//        final String sql = "select * from byrlist where insert_flag=0 and pid>116861 order by pid asc";
//        List<BYRSource> org_byrSources = this.byrSourceDao.getByCustom(new HibernateCallback<List<BYRSource>>() {
//            @Override
//            public List<BYRSource> doInHibernate(Session session) throws HibernateException, SQLException {
//                Query query = session.createSQLQuery(sql).addEntity(BYRSource.class);
//                List pusList = query.list();
//                return pusList;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
////        List<BYRSource> org_byrSources = this.byrSourceDao.find("from BYRSource b where b.insertFlag=0 and b.pid>116861 order by b.pid asc");
//
//        for(int i=0; i<3; i++) {
//            BYRSource byrSource = org_byrSources.get(i);
////            System.out.println(ParseProvince.parse(byrSource.getProvince()));
////            System.out.println(ParseIndustry.parse(byrSource.getIndustry()));
////            System.out.println(ParseType.parse(byrSource.getType()));
//            Job job = new Job();
//            job.setPlace(ParseProvince.parse(byrSource.getProvince()));
//            job.setDescription(byrSource.getJobdetails());
//            job.setCreatedTime(byrSource.getReleaseDate());
//            job.setIndustry(ParseIndustry.parse(byrSource.getIndustry()));
//            job.setPosition(byrSource.getTitle());
//            job.setType(ParseType.parse(byrSource.getType()));
//            job.setUrl(byrSource.getUrl());
//            job.setSourceFrom("2");
//            this.recruitDao.save(job);
//
//            BBSTopic topic = new BBSTopic();
//            BBSSection section = this.sectionDao.findEntity("from BBSSection s where s.companyID=?",companyId);
//            topic.setTitle(position);
//            if(description != null)
//                topic.setBody(description);
//            else
//                topic.setBody(contact);
//            topic.setCreatedTime(new Date());
//            topic.setInputTime(new Date());
//            topic.setBbsSection(section);
//            topic.setUserName("系统");
//            this.topicDao.save(topic);
//            section.setTopics(section.getTopics()+1);
//            this.sectionDao.update(section);
//            job.setTopicID(topic.getId());
//            this.recruitDao.save(job);
//        }

    }




}
