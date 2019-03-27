package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.IpWhitelistDao;
import co.b4pay.admin.entity.IpWhitelist;
import co.b4pay.admin.entity.base.Params;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IP白名单Service
 *
 * @author YK
 * @version $Id: IpWhitelistService.java, v 0.1 2018年4月21日 下午16:45:58 YK Exp $
 */
@Service
@Transactional
public class IpWhitelistService extends CrudService<IpWhitelistDao, IpWhitelist> {
    private static final String CACHE_NAME = "IpWhitelist";

    @Cacheable(value = CACHE_NAME)
    public boolean isAccessAllowed(String merchantId, String ip) {
        List<IpWhitelist> ipWhitelists = dao.findList(Params.create("merchantId", merchantId).add("ip", ip));
        return CollectionUtils.isNotEmpty(ipWhitelists);
    }

//    @Cacheable(value = CACHE_NAME)
//    public Set<String> findByMerchantId(final String merchantId) {
//        List<IpWhitelist> ipWhitelists = dao.findList(Params.create("merchantId", merchantId));
//        if (CollectionUtils.isEmpty(ipWhitelists)) {
//            return Collections.emptySet();
//        }
//        Set<String> ipSet = new HashSet<>();
//        for (IpWhitelist ipWhitelist : ipWhitelists) {
//            ipSet.add(ipWhitelist.getIp());
//        }
//        return ipSet;
//    }
}
