package co.b4pay.admin.common.system.service;

import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.common.system.dao.OrganizationDao;
import co.b4pay.admin.common.system.entity.Organization;
import co.b4pay.admin.common.biz.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织机构服务
 *
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class OrganizationService extends CrudService<OrganizationDao, Organization> {

    @Autowired
    private OrganizationDao organizationDao;

    @Transactional(readOnly = false)
    public Organization createOrganization(Organization organization) {
        if (save(organization) > 0) {
            return organizationDao.get(organization.getId());
        } else
            return null;
    }

    @Transactional(readOnly = false)
    public Organization updateOrganization(Organization organization) {
        if (update(organization) > 0) {
            return organizationDao.get(organization.getId());
        } else
            return null;
    }

    @Transactional(readOnly = false)
    public void deleteOrganization(String organizationId) {
        organizationDao.delete(organizationId);
    }

    public Organization findOne(String organizationId) {
        return organizationDao.get(organizationId);
    }

    public List<Organization> findAll() {
        return organizationDao.findList(Params.empty());
    }

    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        return organizationDao.findAllWithExclude(excludeOraganization);
    }

    @Transactional(readOnly = false)
    public void move(Organization source, Organization target) {
        organizationDao.move1(target.getId().toString(), target.getParentIds(), source.getId().toString());
        organizationDao.move2(target.makeSelfAsParentIds(), source.makeSelfAsParentIds(), source.getId() + "%");
    }
}
