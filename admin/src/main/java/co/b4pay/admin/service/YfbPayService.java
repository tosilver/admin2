package co.b4pay.admin.service;

import co.b4pay.admin.common.YfbUtil.Util;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.dao.YfbPayrollDao;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.YfbPayroll;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suning.epps.codec.Digest;
import com.suning.epps.codec.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class YfbPayService {
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    @Autowired
    private YfbPayrollDao yfbPayrollDao;

    /***
     * 生成签名
     * @param channel
     * @param bussinessParam
     * @return
     */
    protected String calculateSign(Channel channel, String bussinessParam, String type) {
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("merchantNo", channel.getProdPid());
        signMap.put("publicKeyIndex", channel.getProdAppid());
        signMap.put("inputCharset", "UTF-8");
        if (type.equals("query")) {
            signMap.put("batchNo", bussinessParam);
            signMap.put("payMerchantNo", channel.getProdPid());
        } else {
            signMap.put("body", bussinessParam);
        }
//        String wagKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIMIm9vOmbQNSumQYeyWBrXzFxMdTm5qHOtFzk95g88T1UmNGow+jVaugPRvEA16uaGksevje6vZrbCojw+/qrYMUcMRgyWJqGhONf+V3EzjSdgYWgnpX8XjGWkuobaU2VKzuEYYlRA7CH7lyxKqhxml3RHrXAxumki+A3WcVkwXAgMBAAECgYBd7+MEbpVkLg9hZ/+7w0YmVJJbQqxCndIDnPStpximcLp8UysQFZvv44RRN/nTIANKba6bxGaZqaFKP1zoVmjDtbRvNgBwwRLUNWDDJhBDFZAYscaqktmSeTsXNr33ak6RfCOw73aNjY+vIP/wkP1jmdh3blGYK2XsMLyKu8l32QJBANjBJZscTAGbHGay10x0yBGONeehqME43bjRdWnVVzGhN791XW65GxZeuENt8ucYctXlhmpm2FCx7LF2pYjAoiUCQQCawjBEP2V9z31NRh4FreOV3FuGmhfiYSs/BBtncYNrOpzkjTPZsmOGIpaDKIHwnR774j6AtQDfAEE0ai7pJJqLAkAMuTHC7CNOCNPe92qJ82T3GtjxMt0cEoF5Yto4jyxiHmuOUo5cfIMpif/Y/XA/voCnK6T7Q/Q4sGkLdcJ7x4r9AkB19gKYYsJXjWpzFjkYFx1cQ2S4SoheE7bFB7pjtNB5UWn5g++7xg+6VG7pdlzlLL5LPH5r2IvbbvREoZM0PqRVAkAIlGyR8AFWGfoVRqJbC+2t+jj3OS54k2apmuLki5DK8/XGyxNmPEqoI463N2zEag8v4+0L8R1LSaHnvJ05mayX";
        String signature = null;
        try {
            String digest = Digest.digest(Digest.mapToString(Digest.treeMap(signMap)));
            //System.out.println(digest);
//            PrivateKey privateKey = RSAUtil.getPrivateKey(wagKeyString);
            PrivateKey privateKey = RSAUtil.getPrivateKey(channel.getProdPrivateKey());
            //System.out.println(privateKey);
            signature = RSAUtil.sign(digest, privateKey);
            //System.out.println(signature);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return signature;
    }

    /***
     * 保存批次信息
     * @param params
     * @param routerId
     * @return
     */
    protected YfbPayroll saveBatch(JSONObject params, String routerId, Merchant merchant) throws Exception {
        YfbPayroll yfbPayroll = new YfbPayroll();
        yfbPayroll.setMerchantId(params.getString("merchantId"));
        yfbPayroll.setCompany(merchant.getCompany());
        yfbPayroll.setBatchNo(params.getString("batchNo"));
        yfbPayroll.setBatchOrderName(params.getString("batchOrderName"));
        yfbPayroll.setGoodsType(params.getString("goodsType"));
        yfbPayroll.setNotifyUrl(params.getString("notifyUrl"));
        yfbPayroll.setRouterId(routerId);
        yfbPayroll.setTotalAmount(changeF2Y(params.getString("totalAmount")));
        yfbPayroll.setTotalNum(Integer.valueOf(params.getString("totalNum")));
        yfbPayroll.setStatus(1);
        return yfbPayroll;
    }

    /***
     * 封装请求业务数据body
     * @param channel 通道
     * @param params 客户传来的请求参数
     * @param productCode 商品码(写死的)
     * @param yyfUrl 易付宝批付到卡后的回调地址
     * @return JSONObject
     */
    protected JSONArray bulidBatchContentJosn(Channel channel, JSONObject params, String productCode, String yyfUrl) {
        JSONObject contentObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        contentObject.put("batchNo", params.getString("batchNo"));
        contentObject.put("merchantNo", channel.getProdPid());// 70057241;70056575
        contentObject.put("productCode", productCode);
        contentObject.put("totalNum", params.getString("totalNum"));
        contentObject.put("totalAmount", params.getString("totalAmount"));// 40*detailNum
        contentObject.put("currency", "CNY");
        contentObject.put("payDate", DateUtil.YMd_noSpli.format(new Date()));
        contentObject.put("goodsType", params.getString("goodsType"));
        contentObject.put("notifyUrl", yyfUrl);
        contentObject.put("batchOrderName", params.getString("batchOrderName"));
        contentObject.put("detailData", params.getString("detailData"));
        contentObject.put("chargeMode", params.getString("chargeMode"));
        jsonArray.add(contentObject);
        String s1 = jsonArray.toJSONString();
        return jsonArray;
    }

    /**
     * 金额格式有误,返回需要回退的金额
     *
     * @param amount 易付宝那边的返回的金额数量
     * @return 回退的金额
     * @throws Exception
     */
    protected BigDecimal changeF2Y(String amount) throws Exception {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return BigDecimal.valueOf(Long.parseLong(amount)).divide(new BigDecimal(100));
    }

}
