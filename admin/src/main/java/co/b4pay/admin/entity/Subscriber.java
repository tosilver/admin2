package co.b4pay.admin.entity;

import co.b4pay.admin.entity.base.BaseEntity;

/**
 * 客户信息
 */
public class Subscriber extends BaseEntity {
    private String merchantId;   //所属商户Id
    private String name;         //用户名
    private String cardNo;       //银行卡号
    private String idCardNo;     //身份证号

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }
}
