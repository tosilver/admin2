package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.common.util.helper.PasswordHelper;
import co.b4pay.admin.dao.ShangyouDao;
import co.b4pay.admin.entity.SysShangyou;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShangyouService extends CrudService<ShangyouDao, SysShangyou> {
    //todo
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Autowired
    private PasswordHelper passwordHelper;

    @Transactional(readOnly = false)
    public SysShangyou createSYUser(SysShangyou sysShangyou) {
        // 加密密码
        sysShangyou.setSalt(randomNumberGenerator.nextBytes().toHex());
        String password = passwordHelper.encryptPassword(sysShangyou.getPassword(), sysShangyou.getCredentialsSalt());
        sysShangyou.setPassword(password);
        if (save(sysShangyou) > 0) {
            return dao.get(sysShangyou.getId());
        } else {
            return null;
        }
    }
}
