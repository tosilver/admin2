package co.b4pay.admin.service;

import co.b4pay.admin.dao.ChannelDao;
import co.b4pay.admin.dao.XiafaDao;
import co.b4pay.admin.common.biz.service.CrudService;

import co.b4pay.admin.entity.AdminChannel;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.Params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 渠道信息Service
 *
 * @author YK
 * @version $Id: ChannelService.java, v 0.1 2018年4月22日 下午19:31:58 YK Exp $
 */
@Service
@Transactional
public class ChannelService extends CrudService<ChannelDao, Channel> {
    @Autowired
    ChannelDao channelDao;

    public int updateStatus(String id, int status) {
        Params params = Params.create("id", id);
        params.put("status", status);
        return dao.updateStatus(params);
    }

    public List<Channel> findByPayCost(String id) {

        return channelDao.findByPayCost(id);
    }

    public List<Channel> findByProduct(String product) {
        return channelDao.findByProduct(product);
    }

    public List<Channel> selectName() {
        return channelDao.selectName();
    }

    public int saveAdminChannel(AdminChannel adminChannel) {
        if (channelDao.selectAdminChannel(adminChannel).size() < 1) {
            return channelDao.insertAdminChannel(adminChannel);
        } else {
            return channelDao.updateAdminChannel(adminChannel);
        }
    }

    public List<Channel> findByAdmin(String id) {
        return channelDao.findByAdmin(id);
    }

    ;

    public List<Router> selName() {
        return channelDao.selName();
    }

    ;
}
