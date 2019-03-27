package co.b4pay.admin.entity.base;


import java.io.Serializable;

/*
 * 接受代付返回通知*
 */
public class ResponseMode implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String amount;
    private String orderId;
    private String mac;
    private String result_code = "0000";
    private String result_msg;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "ResponseMode{" +
                "amount='" + amount + '\'' +
                ", orderId='" + orderId + '\'' +
                ", mac='" + mac + '\'' +
                ", result_code='" + result_code + '\'' +
                ", result_msg='" + result_msg + '\'' +
                '}';
    }
}
