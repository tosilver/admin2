package co.b4pay.admin.common.system.service;

import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.dao.ResourceDao;
import co.b4pay.admin.common.system.entity.Resource;
import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.biz.exception.ParamValidateException;
import co.b4pay.admin.common.biz.service.CrudService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 资源服务
 *
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends CrudService<ResourceDao, Resource> {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource get(String id) {
        return super.get(id);
    }

    @Override
    @CacheEvict(value = "Resource", allEntries = true)
    @Transactional
    public int update(Resource resource) {
        resource.setUpdateBy(LoginHelper.getUsername());
        return super.update(resource);
    }

    @CacheEvict(value = "Resource", allEntries = true)
    @Transactional(readOnly = false)
    public Resource createResource(Resource resource) {
        save(resource);
        return resource;
    }

    @CacheEvict(value = "Resource", allEntries = true)
    @Transactional(readOnly = false)
    public void move(String id, String action) {
        if (StringUtils.isBlank(id)) {
            throw new ParamValidateException("参数ID不能为空");
        }
        if (!"up".equals(action) && !"down".equals(action)) {
            throw new BizException("仅支持的action[up/down]");
        }
        Resource resource = dao.get(id);

        List<Resource> siblings = findByParentId(resource.getParentId());// 兄弟节点
        if (CollectionUtils.isNotEmpty(siblings)) {
            if (siblings.size() == 1) {
                return;
            }
            Collections.sort(siblings, new Comparator<Resource>() {
                @Override
                public int compare(Resource o1, Resource o2) {
                    return o1.getSort().compareTo(o2.getSort());
                }
            });
            int index = 0;
            for (Resource $resource : siblings) {
                if ($resource.getId().equals(resource.getId())) {
                    break;
                }
                index++;
            }
            if ("up".equals(action)) {// up / down
                if (index - 1 < 0) {
                    throw new BizException("该资源为第一个不能上移");
                }
                Resource preResource = siblings.get(index - 1);
                int tempSort = preResource.getSort();
                preResource.setSort(resource.getSort());
                resource.setSort(tempSort);
                dao.update(preResource);
            } else {
                if (index + 1 == siblings.size()) {
                    throw new BizException("该资源为最后一个不能下移");
                }
                Resource nextResource = siblings.get(index + 1);
                int tempSort = nextResource.getSort();
                nextResource.setSort(resource.getSort());
                resource.setSort(tempSort);
                dao.update(nextResource);
            }
            dao.update(resource);
        }
    }

    @Cacheable(value = "Resource")
    public List<Resource> findAll() {
        return resourceDao.findList(Params.empty());
    }

    public Set<String> findPermissions(Set<String> resourceIds) {
        Set<String> permissions = new HashSet<String>();
        for (String resourceId : resourceIds) {
            Resource resource = get(resourceId);
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Cacheable(value = "Resource")
    public List<Resource> findMenus(Set<String> permissions) {
        List<Resource> menus = new ArrayList<>();
        if (CollectionUtils.isEmpty(permissions)) {
            return menus;
        }
        List<Resource> allResources = findAll();
        for (Resource resource : allResources) {
            if (resource.isRootNode()) {
                continue;
            }
            if (resource.getType() != Resource.ResourceType.MENU) {
                continue;
            }
            if (!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    private boolean hasPermission(Set<String> permissions, Resource resource) {
        if (StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for (String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if (p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }

    public List<Resource> findByParentId(String parentId) {
        return dao.findList(Params.create("parentId", parentId));
    }
}
