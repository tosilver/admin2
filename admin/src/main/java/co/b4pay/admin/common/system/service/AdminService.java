package co.b4pay.admin.common.system.service;

import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.common.system.dao.AdminDao;

import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.common.util.helper.PasswordHelper;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户服务
 *
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class AdminService extends CrudService<AdminDao, Admin> {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RoleService roleService;

    /**
     * 创建用户
     *
     * @param admin
     */
    @Transactional(readOnly = false)
    public Admin createUser(Admin admin) {
        // 加密密码
        admin.setSalt(randomNumberGenerator.nextBytes().toHex());
        String password = passwordHelper.encryptPassword(admin.getPassword(), admin.getCredentialsSalt());
        admin.setPassword(password);
        if (save(admin) > 0) {
            return dao.get(admin.getId());
        } else
            return null;
    }

    @Transactional(readOnly = false)
    public Admin updateUser(Admin admin) {
        if (save(admin) > 0) {
            return dao.get(admin.getId());
        } else
            return null;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    @Transactional(readOnly = false)
    public void changePassword(String userId, String newPassword) {
        if (StringUtils.length(newPassword) < 6) {
            throw new BizException("新密码长度不能少于6位数");
        }
        Admin admin = dao.get(userId);
        /*oldPassword = passwordHelper.encryptPassword(oldPassword, admin.getCredentialsSalt());
        if(StringUtil.notEquals(oldPassword, admin.getPassword())) {
            throw new BizException("原密码错误");
        }*/
        admin.setSalt(randomNumberGenerator.nextBytes().toHex());
        newPassword = passwordHelper.encryptPassword(newPassword, admin.getCredentialsSalt());
        admin.setPassword(newPassword);
        update(admin);
    }

    public Admin findOne(String userId) {
        return dao.get(userId);
    }

    public List<Admin> findAll() {
        return dao.findList(Params.empty());
    }

    /**
     * 根据用户名查找其角色
     *
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        Admin admin = getByUsername(username);
        if (admin == null) {
            return Collections.emptySet();
        }
        return roleService.findRoles(admin.getRoleIds().toArray(new String[0]));
    }

    /**
     * 根据用户名查找其权限
     *
     * @param username
     * @return
     */
    @Cacheable(value = "Admin")
    public Set<String> findPermissions(String username) {
        Admin admin = getByUsername(username);
        if (admin == null) {
            return Collections.emptySet();
        }
        return roleService.findPermissions(admin.getRoleIds());
    }

    /**
     * 根据登录名获取用户
     *
     * @param username
     * @return
     */
    public Admin getByUsername(String username) {
        return dao.getByUsername(username);
    }

    /**
     * 启用用户
     *
     * @param id
     */
    @Transactional
    public void startUsing(String id) {
        dao.startUsing(id);
    }

    public Admin findByMerchantId(String merchantIds) {
        return dao.findByMerchantId(merchantIds);
    }

}
