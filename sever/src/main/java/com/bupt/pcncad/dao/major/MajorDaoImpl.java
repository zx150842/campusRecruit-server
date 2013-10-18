package com.bupt.pcncad.dao.major;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.Major;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-11
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MajorDaoImpl extends HibernateGenericDaoImpl<Major, Integer> implements IMajorDao {
}
