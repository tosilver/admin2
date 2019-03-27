package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.dao.MallAccessControlDao;
import co.b4pay.admin.dao.MallAddressDao;
import co.b4pay.admin.entity.MallAccessControl;
import co.b4pay.admin.entity.MallAddress;
import co.b4pay.admin.entity.Withdraw;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author YK
 * @version $Id: ConsumeService.java, v 0.1 2018年4月21日 上午11:45:58 YK Exp $
 */
@Service
@Transactional
public class MallAddressControlService extends CrudService<MallAccessControlDao, MallAccessControl> {

    @Autowired
    private MallAddressDao mallAddressDao;

    @Override
    public Page<MallAccessControl> findPage(Page<MallAccessControl> page) {
        Params params = page.getParams();

        if (params == null) {
            params = Params.create();
        }
        int totalCount = dao.count(params);
        if (totalCount < 1) {
            return page;
        }
        page.init(totalCount);
        int pageIndex = page.getPageIndex();
        int totalPage = page.getTotalPage();
        if (totalPage < pageIndex) {
            page.setPageIndex(1);
            page.setPageFirst(0);
        }
        params.initPage(page);
        List<MallAccessControl> list = dao.findList(params);
        for (MallAccessControl mallAccessControl : list) {
            String zfbAccess = mallAccessControl.getZfbAccess();
            String[] split = zfbAccess.split(",");
            String mallName ="";
            for (String s : split) {
                MallAddress mallAddress = mallAddressDao.get(s);
                mallName += mallAddress.getMallName()+",";
            }
            mallAccessControl.setZfbAccessName(mallName);
        }
        page.setList(list);
        return page;
    }
}
