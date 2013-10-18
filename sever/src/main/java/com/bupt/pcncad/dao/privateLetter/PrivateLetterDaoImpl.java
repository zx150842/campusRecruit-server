package com.bupt.pcncad.dao.privateLetter;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.PrivateLetter;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-15
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PrivateLetterDaoImpl extends HibernateGenericDaoImpl<PrivateLetter, Integer> implements IPrivateLetterDao {
}
