package co.b4pay.admin.service;

import co.b4pay.admin.dao.MerchantFunctionDao;
import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.biz.service.CrudService;

import co.b4pay.admin.entity.MerchantFunction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商户接口Service
 *
 * @author YK
 * @version $Id: MerchantFunctionService.java, v 0.1 2018年4月20日 下午14:20:58 YK Exp $
 */
@Service
@Transactional
public class MerchantFunctionService extends CrudService<MerchantFunctionDao, MerchantFunction> {
    //    private static final String CACHE_NAME = "MerchantFunction";
    @Override
    public int save(MerchantFunction merchantFunction) {
        if (dao.existsMerchantFunction(merchantFunction) > 0) {
            throw new BizException("商户接口已经存在，请勿重复添加");
        }
        return super.save(merchantFunction);
    }
}
