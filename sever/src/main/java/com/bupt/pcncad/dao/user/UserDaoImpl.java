package com.bupt.pcncad.dao.user;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-26
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends HibernateGenericDaoImpl<User, String> implements IUserDao {
}
