package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

public class ApkFile extends BaseEntity {
    private String name;   //APK名
    private MultipartFile file;

    private String version; //APK版本

    private String fileUrl; //APK路径

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
