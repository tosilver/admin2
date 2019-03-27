package com.suning.test;


import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.dao.DetailDataCardDao;
import co.b4pay.admin.entity.DetailDataCard;
import co.b4pay.admin.entity.Xiafa;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.XiafaService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suning.dto.BatchWithdrawData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/spring-*.xml")
public class PFTest {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private XiafaService xiafaService;


    @Autowired
    DetailDataCardDao detailDataCardDao;

    private List<Xiafa> XiafaList = null;

    //@Before
    public void getCard() {
        try {
            //从银行码表中读出银行名称键值对
            new POIExcelDemo();
            Map<String, List<List<String>>> bankMap = POIExcelDemo.read("");
            List<List<String>> bankList = bankMap.get("Sheet1");
            Map<String, String> nameCodeMap = new HashMap<>();
            for (int i = 1; i < bankList.size(); i++) {
                List<String> row = bankList.get(i);
                nameCodeMap.put(row.get(0), row.get(1));
            }
            //从下发表格中读出下发信息
            Map<String, List<List<String>>> cardMap = POIExcelDemo.read("G:\\Users\\zenggp\\Desktop\\下发表格模板 - 副本.xlsx");
            List<List<String>> cardList = cardMap.get("Sheet1");
            XiafaList = new ArrayList<>();
            for (int i = 1; i < cardList.size(); i++) {
                List<String> row = cardList.get(i);
                Xiafa xiafa = new Xiafa();

                xiafa.setMerchantId(100000000000005L);
                xiafa.setReceiverName(row.get(1));
                xiafa.setReceiverCardNo(row.get(2));
                xiafa.setBankName(row.get(3));
                xiafa.setBankCode(nameCodeMap.get(row.get(3)));
                String val = row.get(4);
                System.out.println("金额：" + val);
                xiafa.setAmount(new BigDecimal(val));
                xiafa.setStatus(0);

                XiafaList.add(xiafa);
                xiafaService.save(xiafa);
            }
            XiafaList.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        BatchWithdrawData batchWithdrawData = new BatchWithdrawData();
        String baseUrl = "http://admin.b4bisi.com/payroll/yfbWithPay.do";
        try {
            //for (Xiafa xiafa : XiafaList) {
            //Thread.sleep(1000);
            Xiafa xiafa = new Xiafa();
            xiafa.setReceiverCardNo("6217003370020831515");
            xiafa.setReceiverName("文少虎");
            xiafa.setBankName("建设银行");
            xiafa.setAmount(BigDecimal.valueOf(1));
            xiafa.setMerchantId(100000000000005L);
            String secretKey = merchantService.get(xiafa.getMerchantId().toString()).getSecretKey();
            System.out.println(secretKey);
            String responseData = batchWithdrawData.batchWithDraw(baseUrl, secretKey, xiafa);
            System.out.println("批量出款responseData:" + responseData);
            //}
        } catch (Exception e) {
            System.out.println("调用批量出款出错：" + e);
        }
    }

    @Test
    public void query() {

     /*   DetailDataCard detailDataCard = new DetailDataCard();
        detailDataCard.setStatus(1);
        detailDataCard.setMerchantId(100000000000056L);
        Params queryParams = Params.create(detailDataCard);
        queryParams.add("startDate", "2018-12-30 00:00:00");
        queryParams.add("endDate", "2018-12-30 17:00:00");
        List<DetailDataCard> dataCardList = detailDataCardDao.findList(queryParams);
*/

        String baseUrl = "http://admin.b4bisi.com/payroll/yfbWitdrawQuery.do";

        HashMap<String, String> params = new HashMap<>();
        params.put("merchantId", "100000000000005");
        //params.put("batchNo", dataCardList.get(0).getBatchNo());
        params.put("batchNo", "1102439442364235776");
        params.put("key", merchantService.get(params.get("merchantId")).getSecretKey());

        HttpClient httpClient = new HttpClient(baseUrl);
        httpClient.addParameter("merchantId", params.get("merchantId"));
        httpClient.addParameter("batchNo", params.get("batchNo"));
        httpClient.addParameter("mac", getQueryMac(params));
        try {
            httpClient.post();
            String content = httpClient.getContent();
            System.out.println(content);
            /*String replace = content.replace("\\", "");
            System.out.println(replace);*/
            /*Map rspJson = JSON.parseObject(replace,Map.class);
            System.out.println(rspJson);*/
            /*int beginIndex = replace.indexOf("status");
            String substring = replace.substring(beginIndex-1, beginIndex + 12);
            System.out.println("{"+substring+"}");
            Object parse = JSONObject.parseObject(substring,Map.class);
            System.out.println(parse);*/
           /* ;
            String msg = rspJson.getString("msg");
            JSONObject msgJson = JSONObject.parseObject("msg");
            String status = msgJson.getString("status");
            System.out.println(status);*/
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testban() {
        String baseUrl = "http://admin.b4bisi.com/merchant/selMoney";
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "100000000000005");
        StringBuffer sf = new StringBuffer();
        sf.append("?");
        sf.append("id=").append(params.get("id"));
        sf.append("&mac=").append(getQuerybanMac(params));
        System.out.println(sf);
        HttpClient httpClient = new HttpClient(baseUrl + sf);
        try {
            httpClient.get();
            String content = httpClient.getContent();
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private String getQuerybanMac(Map<String, String> map) {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("100000000000005");
            String s = Md5Encrypt.md5(sf.toString()).toUpperCase();
            System.out.println(s);
            return s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getQueryMac(Map<String, String> map) {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("merchantId=").append(map.get("merchantId"));
            sf.append("&batchNo=").append(map.get("batchNo"));
            sf.append("&key=").append(map.get("key"));
            return Md5Encrypt.md5(sf.toString()).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
