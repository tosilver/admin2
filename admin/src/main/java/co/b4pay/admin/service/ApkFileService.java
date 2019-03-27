package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.ApkFileDao;
import co.b4pay.admin.entity.ApkFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApkFileService extends CrudService<ApkFileDao, ApkFile> {
    @Autowired
    ApkFileDao apkFileDao;

    public ApkFile findByVersion(String version, String name) {
        return apkFileDao.findByVersion(version, name);

    }
}
