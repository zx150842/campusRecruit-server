package com.bupt.pcncad.dao.source;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.source.BYRSource;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-15
 * Time: 下午6:12
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BYRSourceDaoImpl extends HibernateGenericDaoImpl<BYRSource, Integer> implements IBYRSourceDao {
}
