package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.JobTrade;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;

import co.b4pay.admin.entity.Consume;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消费记录DAO
 *
 * @author YK
 * @version $Id: ConsumeDao.java, v 0.1 2018年4月21日 上午11:41:12 YK Exp $
 */
@MyBatisDao
public interface ConsumeDao extends ICrudDao<Consume> {
    //查询分账总金额
    Double sumFzMoney(Params params);

    //查询分账笔数
    int fzCount(Params params);

    //查询分账笔数
    int fzFailCount(Params params);

    //查询总金额
    Double sumMoney(Params params);

    //查询实际请求金额
    Double sumRequestMoney(Params params);

    Double sumAccountMoney(Params params);

    Double getSuccessRate(Params params);

    int accountCount(Params params);

    int updateStatus(Params params);

    int updateTrade(Params params);

    List<Consume> findByDerived(HashMap<String, Object> map);

    List<Consume> selFind(Map<String, Object> map);

    int selFindCount(String id);

    String findAdminChannel(String id);

    Page<Consume> findListCount(Page<Consume> page);

    JobTrade findJobTradeById(String id);

}