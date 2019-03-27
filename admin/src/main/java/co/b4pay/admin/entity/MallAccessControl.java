package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

import java.util.List;
import java.util.Map;

/****
 * 商城地址权限控制
 * @author Tosdom
 */
public class MallAccessControl extends BaseEntity {


    private Long merchantId;                 //商户号
    private String merchantName;            //商户名称
    private String zfbAccess;               //支付宝通道
    private String zfbAccessName;           //支付宝通道名称
    private String wxAccess;                //微信通道
    private String wxAccessName;            //微信通道名称
    private String kjAccess;                //快捷通道
    private String kjAccessName;            //快捷通道名称
    private String test;                    //预留字段


    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getZfbAccess() {
        return zfbAccess;
    }

    public void setZfbAccess(String zfbAccess) {
        this.zfbAccess = zfbAccess;
    }

    public String getWxAccess() {
        return wxAccess;
    }

    public void setWxAccess(String wxAccess) {
        this.wxAccess = wxAccess;
    }

    public String getKjAccess() {
        return kjAccess;
    }

    public void setKjAccess(String kjAccess) {
        this.kjAccess = kjAccess;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getZfbAccessName() {
        return zfbAccessName;
    }

    public void setZfbAccessName(String zfbAccessName) {
        this.zfbAccessName = zfbAccessName;
    }

    public String getWxAccessName() {
        return wxAccessName;
    }

    public void setWxAccessName(String wxAccessName) {
        this.wxAccessName = wxAccessName;
    }

    public String getKjAccessName() {
        return kjAccessName;
    }

    public void setKjAccessName(String kjAccessName) {
        this.kjAccessName = kjAccessName;
    }
}
