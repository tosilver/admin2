package co.b4pay.admin.dao;

import co.b4pay.admin.common.biz.dao.ICrudDao;
import co.b4pay.admin.common.biz.dao.annotation.MyBatisDao;
import co.b4pay.admin.entity.ApkFile;

@MyBatisDao
public interface ApkFileDao extends ICrudDao<ApkFile> {
    ApkFile findByVersion(String version, String name);
}
