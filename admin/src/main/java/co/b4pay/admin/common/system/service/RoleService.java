package co.b4pay.admin.common.system.service;

import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.common.system.dao.RoleDao;
import co.b4pay.admin.common.system.entity.Role;
import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限服务
 *
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<ICrudDao<Role>, Role> {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceService resourceService;

    @Override
    @CacheEvict(value = "Role", allEntries = true)
    @Transactional(readOnly = false)
    public int save(Role entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "Role", allEntries = true)
    @Transactional(readOnly = false)
    public int update(Role entity) {
        return super.update(entity);
    }

    @CacheEvict(value = "Role", allEntries = true)
    @Transactional(readOnly = false)
    public void deleteRole(String roleId) {
        roleDao.delete(roleId);
    }

    @Cacheable(value = "Role")
    public List<Role> findAll() {
        return roleDao.findList(Params.empty());
    }

    public Set<String> findRoles(String... roleIds) {
        Set<String> roles = new HashSet<String>();
        for (String roleId : roleIds) {
            Role role = get(roleId);
            if (role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    public Set<String> findPermissions(List<String> roleIds) {
        Set<String> resourceIds = new HashSet<String>();
        for (String roleId : roleIds) {
            Role role = get(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
