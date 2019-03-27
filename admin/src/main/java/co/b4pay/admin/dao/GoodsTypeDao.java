package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.GoodsType;
import co.b4pay.admin.entity.Router;


/**
 * 商品类别
 * Created by john on 2018/6/13.
 */
@MyBatisDao
public interface GoodsTypeDao extends ICrudDao<GoodsType> {
}
