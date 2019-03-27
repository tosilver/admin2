package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.XiafaDao;
import co.b4pay.admin.entity.AdminChannel;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.Xiafa;
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
public class XiafaService extends CrudService<XiafaDao, Xiafa> {
}
