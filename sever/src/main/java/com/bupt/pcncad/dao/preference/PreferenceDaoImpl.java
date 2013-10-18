package com.bupt.pcncad.dao.preference;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.Preference;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PreferenceDaoImpl extends HibernateGenericDaoImpl<Preference, Integer> implements IPreferenceDao {
}
