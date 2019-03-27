/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: BatchTransferData.java
 * Author:   16031333
 * Date:     2016年5月25日 上午10:30:43
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package co.b4pay.admin.entity.dto;

import co.b4pay.admin.common.util.IdWorker;
import co.b4pay.admin.common.xifaUtil.HttpClientUtil;
import co.b4pay.admin.common.xifaUtil.Util;
import co.b4pay.admin.entity.Xiafa;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 〈一句话功能简述〉<br>
 * 批量出款 数据拼写
 *
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchWithdrawData {

    private String publicKeyIndex = "xpy1";
    private String signAlgorithm = "RSA";
    private String merchantNo = "suning";
    private String inputCharset = "UTF-8";

    String batchNo;

    private IdWorker idWorker = new IdWorker();


    /**
     * 请求转账网关
     *
     * @param baseUrl 请求业务数据 batchNum 请求的批次数目 detailNum 单批次中的明细数目
     * @param xiafa
     * @return signature 签名
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public String batchWithDraw(String baseUrl, String secretKey, Xiafa xiafa) throws Exception {
        //构造代付参数
        String bussinessParam = bulidBatchContentJosn(1, xiafa).toJSONString();
        System.out.println(bussinessParam);
        String responseStr = HttpClientUtil.post("1",
                xiafa.getMerchantId().toString(),
                baseUrl,
                Util.getPayMac(xiafa.getMerchantId().toString(), secretKey, bussinessParam), bussinessParam);
        return responseStr;
    }


    /**
     * 生成body批次数据
     *
     * @param
     * @param xiafa
     * @return JSONObject
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public JSONObject bulidBatchContentJosn(int detailNum, Xiafa xiafa) {
        JSONObject contentObject = new JSONObject();
        String batchNo = idWorker.nextId() + "";
        System.out.println("batchNo:" + batchNo);

        //批付id动态生成
        contentObject.put("batchNo", batchNo);

        contentObject.put("merchantId", "100000000000005");
        contentObject.put("totalNum", 1);

        //批付金额
        contentObject.put("totalAmount", (int) (xiafa.getAmount().doubleValue() * 100));

        contentObject.put("chargeMode", "03");
        contentObject.put("goodsType", "220072");
        contentObject.put("batchOrderName", "batchOrderName");
        contentObject.put("notifyUrl", "http://localhost:8080");
        contentObject.put("productCode", "01070000042");

        contentObject.put("detailData", bulidDetailContentJosn(detailNum, xiafa));
        System.out.println("||contentObject:" + contentObject.toJSONString());
        System.out.println("----------------------------------");
        return contentObject;
    }

    /**
     * 生成body明细数据
     *
     * @param
     * @param xiafa
     * @return JSONObject
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public JSONArray bulidDetailContentJosn(int detailNum, Xiafa xiafa) {
        JSONArray detailArray = new JSONArray();
        for (int i = 0; i < detailNum; i++) {
            JSONObject detailObject = new JSONObject();
            detailObject.put("serialNo", idWorker.nextId());

            //银行卡号动态获取
            detailObject.put("receiverCardNo", xiafa.getReceiverCardNo());
            //收款人姓名
            detailObject.put("receiverName", xiafa.getReceiverName());

            detailObject.put("receiverType", "PERSON");
            //detailObject.put("receiverCurrency", "CNY");

            //收款银行
            detailObject.put("bankName", xiafa.getBankName());
            //收款银行代号
            detailObject.put("bankCode", xiafa.getBankCode());

            detailObject.put("bankProvince", "");
            detailObject.put("bankCity", "");
            detailObject.put("payeeBankLinesNo", "145214214544");

            //收款金额
            detailObject.put("amount", (int) (xiafa.getAmount().doubleValue() * 100));
            //下发记录的id
            detailObject.put("xiafaId", xiafa.getId());

            detailObject.put("orderName", "orderName");
            detailObject.put("remark", "测试");
            System.out.println("||detailObject:" + detailObject.toJSONString());
            System.out.println("----------------------------------");
            detailArray.add(detailObject);
        }
        return detailArray;

    }

}
