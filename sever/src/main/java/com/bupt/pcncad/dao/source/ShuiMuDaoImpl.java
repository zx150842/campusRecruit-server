package com.bupt.pcncad.dao.source;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.source.ShuiMuBBSSource;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-15
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ShuiMuDaoImpl extends HibernateGenericDaoImpl<ShuiMuBBSSource,Integer> implements IShuiMuDao {
}
