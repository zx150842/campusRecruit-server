package com.bupt.pcncad.dao.recruit;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.Job;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RecruitDaoImpl extends HibernateGenericDaoImpl<Job, Integer> implements IRecruitDao {
}
