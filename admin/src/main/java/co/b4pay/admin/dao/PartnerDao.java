package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;

import co.b4pay.admin.entity.Partner;


/**
 * 合作商 Dao
 * Created by john on 2018/4/27.
 */
@MyBatisDao
public interface PartnerDao extends ICrudDao<Partner> {
}
