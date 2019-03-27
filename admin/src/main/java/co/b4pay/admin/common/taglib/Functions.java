package co.b4pay.admin.common.taglib;

import co.b4pay.admin.common.system.entity.Resource;
import co.b4pay.admin.common.system.service.OrganizationService;
import co.b4pay.admin.common.system.service.ResourceService;
import co.b4pay.admin.common.system.entity.Organization;
import co.b4pay.admin.common.system.entity.Role;

import co.b4pay.admin.common.system.service.RoleService;
import co.b4pay.admin.common.core.config.MainConfig;
import co.b4pay.admin.common.core.spring.SpringUtils;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 标签函数
 *
 * @author cc
 */
public class Functions {

    public static boolean in(Iterable<?> iterable, Object element) {
        if (iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }

    public static String principal(Session session) {
        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (principalCollection == null)
            return "";
        else
            return (String) principalCollection.getPrimaryPrincipal();
    }

    public static boolean isForceLogout(Session session) {
        return session.getAttribute(MainConfig.SESSION_FORCE_LOGOUT_KEY) != null;
    }

    public static String organizationName(String organizationId) {
        Organization organization = getOrganizationService().findOne(organizationId);
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    public static String organizationNames(Collection<String> organizationIds) {
        if (CollectionUtils.isEmpty(organizationIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (String organizationId : organizationIds) {
            Organization organization = getOrganizationService().findOne(organizationId);
            if (organization == null) {
                return "";
            }
            s.append(organization.getName());
            s.append(",");
        }

        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    public static String roleName(String roleId) {
        Role role = getRoleService().get(roleId);
        if (role == null) {
            return "";
        }
        return role.getDescription();
    }

    public static String roleNames(Collection<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (String roleId : roleIds) {
            Role role = getRoleService().get(roleId);
            if (role == null) {
                return "";
            }
            s.append(role.getDescription());
            s.append(",");
        }

        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    public static String resourceName(String resourceId) {
        Resource resource = getResourceService().get(resourceId);
        if (resource == null) {
            return "";
        }
        return resource.getName();
    }

    public static String resourceNames(Collection<String> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (String resourceId : resourceIds) {
            Resource resource = getResourceService().get(resourceId);
            if (resource == null) {
                return "";
            }
            s.append(resource.getName());
            s.append(",");
        }

        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    private static OrganizationService organizationService;
    private static RoleService roleService;
    private static ResourceService resourceService;

    public static OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = SpringUtils.getBean(OrganizationService.class);
        }
        return organizationService;
    }

    public static RoleService getRoleService() {
        if (roleService == null) {
            roleService = SpringUtils.getBean(RoleService.class);
        }
        return roleService;
    }

    public static ResourceService getResourceService() {
        if (resourceService == null) {
            resourceService = SpringUtils.getBean(ResourceService.class);
        }
        return resourceService;
    }

}
