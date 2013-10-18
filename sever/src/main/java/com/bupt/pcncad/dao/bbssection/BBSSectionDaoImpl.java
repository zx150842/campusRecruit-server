package com.bupt.pcncad.dao.bbssection;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.BBSSection;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 上午12:29
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BBSSectionDaoImpl extends HibernateGenericDaoImpl<BBSSection, Integer> implements IBBSSectionDao {
}
