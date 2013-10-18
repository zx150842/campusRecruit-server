package com.bupt.pcncad.dao.ctsource;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.source.CTSource;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-11
 * Time: 下午11:02
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CTSourceDaoImpl extends HibernateGenericDaoImpl<CTSource, Integer> implements ICTSourceDao {
}
