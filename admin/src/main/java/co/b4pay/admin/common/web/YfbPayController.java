package co.b4pay.admin.common.web;

import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.biz.exception.DaoException;
import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.entity.Channel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.suning.epps.codec.Digest;
import com.suning.epps.merchantsignature.SignatureUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public abstract class YfbPayController {
    /***
     * 获取请求商户ID
     * @param request
     * @return
     */
    protected Long getMerchantId(HttpServletRequest request) {
        String merchantId = request.getParameter("merchantId");
        return isBlank(merchantId) ? null : Long.parseLong(merchantId);
    }

    protected boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

    /***
     * 封装校验下游请求参数
     * @param request
     * @return
     * @throws BizException
     */
    protected JSONObject getParams(HttpServletRequest request) throws BizException {
        String body = request.getParameter("body");
        System.out.println(request.getParameter("body"));
        try {
            JSONObject params = JSONObject.parseObject(body);

            for (String paramName : getRequiredParams()) { // 必需参数
                //System.out.println(getRequiredParams());
                String paramValue = params.getString(paramName);
                if (StringUtil.isBlank(paramValue)) {
                    throw new BizException(String.format("缺少参数[%s]", paramName));
                }

            }
            return params;

        } catch (JSONException jsonException) {
            throw new BizException("body格式错误");
        }

    }

    /**
     * 下游下单MD5验签
     *
     * @param request
     * @param merchantSecretKey
     * @return
     * @throws IOException
     */
    protected String getPayMac(HttpServletRequest request, String merchantSecretKey) throws IOException {
        StringBuffer sf = new StringBuffer();
        sf.append("merchantId=").append(request.getParameter("merchantId"));
        //System.out.println(request.getParameter("merchantId"));
        sf.append("&body=").append(request.getParameter("body"));
        //System.out.println(request.getParameter("body"));
        sf.append("&key=").append(merchantSecretKey);
        //System.out.println(merchantSecretKey);
        return Md5Encrypt.md5(sf.toString()).toUpperCase();
    }

    /***
     * 下游查询订单MD5验签
     * @param request
     * @param merchantSecretKey
     * @return
     * @throws IOException
     */
    protected String getQueryMac(HttpServletRequest request, String merchantSecretKey) throws IOException {
        StringBuffer sf = new StringBuffer();
        sf.append("merchantId=").append(request.getParameter("merchantId"));
        sf.append("&batchNo=").append(request.getParameter("batchNo"));
        sf.append("&key=").append(merchantSecretKey);
        return Md5Encrypt.md5(sf.toString()).toUpperCase();
    }

    /***
     * 易付宝批付上游通知验签
     * @param request
     * @param channel
     * @return
     */
    protected boolean attestation(HttpServletRequest request, Channel channel) {
        /**
         * base64url替换补齐
         */
        String content = request.getParameter("content");
        String sign = request.getParameter("sign");
        String sign1 = sign.replace("-", "+");
        String sign2 = sign1.replace("_", "/");
        StringBuilder sb = new StringBuilder();
        sb.append(sign2);
        for (int i = 0; i < 4; i++) {
            int length = sb.length();
            if (length % 4 == 0) {
                break;
            }
            sb.append("=");
        }
        String s = sb.toString();
        //获取平台公钥
        String prodPublicKey = channel.getProdPublicKey();
        /**
         * 参数值MD5摘要
         */
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("content", content);

        String digest = Digest.digest(Digest.mapToString(Digest.treeMap(signMap)));
        try {
            //Signature验签
            return SignatureUtil.verifySignature(digest, s, prodPublicKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected String[] getRequiredParams() {
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    protected String[] getOptionalParams() {
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }
}
