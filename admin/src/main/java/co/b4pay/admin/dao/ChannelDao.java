package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.AdminChannel;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.Params;

import java.util.List;

/**
 * 渠道信息DAO
 *
 * @author YK
 * @version $Id: ChannelDao.java, v 0.1 2018年4月21日 下午22:09:12 YK Exp $
 */
@MyBatisDao
public interface ChannelDao extends ICrudDao<Channel> {

    int updateStatus(Params params);

    List<Channel> findByPayCost(String id);

    List<Channel> findByProduct(String product);

    List<Channel> selectName();

    List<AdminChannel> selectAdminChannel(AdminChannel adminChannel);

    int insertAdminChannel(AdminChannel adminChannel);

    int updateAdminChannel(AdminChannel adminChannel);

    List<Channel> findByAdmin(String id);

    List<Router> selName();

    List<Channel> findByDstDescribe(String dstDescribe);
}