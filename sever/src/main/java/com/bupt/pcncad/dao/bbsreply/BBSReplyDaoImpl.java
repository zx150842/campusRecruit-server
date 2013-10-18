package com.bupt.pcncad.dao.bbsreply;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.BBSReply;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-27
 * Time: 上午12:36
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BBSReplyDaoImpl extends HibernateGenericDaoImpl<BBSReply, Integer> implements IBBSReplyDao {
}
