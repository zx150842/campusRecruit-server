package com.bupt.pcncad.dao.bbstopic;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.BBSTopic;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 上午12:32
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BBSTopicDaoImpl extends HibernateGenericDaoImpl<BBSTopic, Integer> implements IBBSTopicDao {
}
