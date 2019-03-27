package co.b4pay.admin.common.core.api.qiniu;

/**
 * 上传凭证
 *
 * @author YK
 * @version $Id: UploadToken.java, v 0.1 2016年5月5日 上午11:56:43 YK Exp $
 */
public class UploadToken {

    private String uptoken;

    public UploadToken() {
    }

    public UploadToken(String uptoken) {
        super();
        this.uptoken = uptoken;
    }

    public String getUptoken() {
        return uptoken;
    }

    public void setUptoken(String uptoken) {
        this.uptoken = uptoken;
    }

}
