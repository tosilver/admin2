package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;

import co.b4pay.admin.entity.IpWhitelist;


/**
 * IP白名单DAO
 *
 * @author YK
 * @version $Id: IpWhitelistDao.java, v 0.1 2018年4月21日 下午16:59:12 YK Exp $
 */
@MyBatisDao
public interface IpWhitelistDao extends ICrudDao<IpWhitelist> {

}