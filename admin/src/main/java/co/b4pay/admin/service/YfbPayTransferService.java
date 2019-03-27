package co.b4pay.admin.service;

import co.b4pay.admin.common.YfbUtil.HttpClientUtil;
import co.b4pay.admin.common.YfbUtil.PayrollConstants;
import co.b4pay.admin.controller.payroll.YfbPayTransferController;
import co.b4pay.admin.dao.*;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.DetailDataAccount;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.YfbPayroll;
import co.b4pay.admin.entity.base.AjaxResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.*;
import java.util.List;

/****
 // *  易付宝请求批付到易付宝 暂不用
 */
@Service
public class YfbPayTransferService extends YfbPayService {
    private static final Logger logger = LoggerFactory.getLogger(YfbPayTransferService.class);
    //产品名huan
    private static final String ROUTER_KEY = "yfbPayroll";//睿悦
    //    private static final String ROUTER_KEY = "aliH5Pay";//威创
    //签名方式
    private static final String signAlgorithm = "RSA";
    //签名指定字符集
    private static final String inputCharset = "UTF-8";
    //产品编号
    private static final String productCode = "01060000029";

    //测试数据
//    private static final String test = "ssssss";
//    private static final String TestUrl = "http://admin.sssx.red/payroll/yfbWithPay.do";
//    private static final String Skr = "";
    @Autowired
    ChannelDao channelDao;
    @Autowired
    YfbPayrollDao yfbPayrollDao;
    @Autowired
    DetailDataAccountDao detailDataAccountDao;
    @Autowired
    MerchantDao merchantDao;

    /**
     * 请求网关
     *
     * @param merchantId
     * @param params
     * @return
     */
    public AjaxResponse executeTransfer(Long merchantId, JSONObject params, BigDecimal balance, String routerId) {

        //根据路由id得到渠道
        List<Channel> channels = channelDao.findByPayCost(routerId);
        Channel channel = channels.get(0);
        //根据商户id得到商户
        Merchant merchant = merchantDao.get(merchantId.toString());
        //得到生产环境appid
        String publicKeyIndex = channel.getProdAppid();
        //得到生产环境商户id
        String merchantNo = channel.getProdPid();

        String bussinessParam = bulidBatchContentJosn(channel, params, productCode, PayrollConstants.YYF_YFB_TF_URL).toJSONString();
        //根据要求格式化封装好的数据
        bussinessParam = bussinessParamBody(bussinessParam);

        String signature = null;
        System.out.println("进来了:" + this.getClass().getSimpleName());
        //System.out.println(id);
        signature = calculateSign(channel, bussinessParam, "transferPlace");
        //System.out.println(signature);
        YfbPayTransferController yf = new YfbPayTransferController();

        try {
            //singnature
            String responseStr = HttpClientUtil.post(publicKeyIndex, signAlgorithm, merchantNo, inputCharset, PayrollConstants.YFB_URL_TF,
                    signature, bussinessParam);
            //System.out.println(responseStr);
            JSONObject jsonObject = JSONObject.parseObject(responseStr);
            String responseCode = jsonObject.getString("responseCode");
            if (responseCode.equals("0000")) {
                merchant.setBalance(balance);
                merchantDao.update(merchant);
                YfbPayroll yfbPayroll = saveBatch(params, routerId, merchant);
                int i = yfbPayrollDao.insert(yfbPayroll);
                if (i > 0) {
                    logger.warn(params.getString("batchNo") + "批付订单保存成功");
                    saveAccount(params, merchantId);
                } else {
                    logger.warn(params.getString("batchNo") + "批付订单保存失败");
                }
                return AjaxResponse.success(jsonObject.toJSONString());
            } else {
                return AjaxResponse.failure(jsonObject.toJSONString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return responseStr;
        return null;

    }

    private void saveAccount(JSONObject params, Long merchantId) {
        String detailData = params.getString("detailData");
        JSONArray objects = JSONArray.parseArray(detailData);
        for (int i = 0; i < objects.size(); i++) {
            DetailDataAccount detailDataAccount = new DetailDataAccount();
            JSONObject jsonObject = objects.getJSONObject(i);
            detailDataAccount.setMerchantId(merchantId);
            detailDataAccount.setCompany(merchantDao.get(merchantId.toString()).getCompany());
//            detailDataAccount.setSerialNo("12321412123");
            detailDataAccount.setSerialNo(jsonObject.getString("serialNo"));
            System.out.println("SERO:" + jsonObject.getString("serialNo"));
            detailDataAccount.setOrderName(jsonObject.getString("orderName"));
            try {
                detailDataAccount.setReceiverLoginName(jsonObject.getString("receiverLoginName"));
                detailDataAccount.setReceiverName(jsonObject.getString("receiverNo"));
            } catch (JSONException j) {
                detailDataAccount.setReceiverNo(jsonObject.getString("receiverName"));
            }
            detailDataAccount.setAmount(new BigDecimal(jsonObject.getString("amount")));
            detailDataAccount.setReceiverType(jsonObject.getString("receiverType"));
            detailDataAccount.setBatchNo(params.getString("batchNo"));
            int insert = detailDataAccountDao.insert(detailDataAccount);
            if (insert > 0) {
                logger.warn(jsonObject.getString("serialNo") + "订单保存成功");
            } else {
                logger.warn(jsonObject.getString("serialNo") + "订单保存失败");
            }
        }
    }

    public String bussinessParamBody(String bussinessParam) {
        bussinessParam = bussinessParam.replace("\"[{", "[*{");
        bussinessParam = bussinessParam.replace("}}\"", "}*]");
        bussinessParam = bussinessParam.replace("[{", "{");
        bussinessParam = bussinessParam.replace("}]", "}");
        bussinessParam = bussinessParam.replace("[*{", "[{");
        bussinessParam = bussinessParam.replace("}*]", "}]");
        bussinessParam = bussinessParam.replace("\\\"", "\"");
        bussinessParam = bussinessParam.replace("}\"", "}]");
        return bussinessParam;
    }
}