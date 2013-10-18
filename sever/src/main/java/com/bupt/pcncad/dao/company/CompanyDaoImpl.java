package com.bupt.pcncad.dao.company;

import com.bupt.pcncad.dao.HibernateGenericDaoImpl;
import com.bupt.pcncad.domain.Company;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-8-25
 * Time: 下午10:17
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CompanyDaoImpl extends HibernateGenericDaoImpl<Company, Integer> implements ICompanyDao {
}
