package co.b4pay.admin.controller.xiafa;

import co.b4pay.admin.common.YfbUtil.PayrollConstants;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.smsUtil.SmsUtil;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.common.xifaUtil.POIExcelUtil;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.entity.dto.BatchWithdrawData;
import co.b4pay.admin.service.*;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/****
 * 易付宝批付到卡下单接口
 */
@Controller
@RequestMapping("/xiafa")
@SessionAttributes("code")
public class XiafaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(XiafaController.class);

    @Autowired
    private XiafaService xiafaService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ChannelService channelService;

    private Page<Xiafa> pac = new Page<>();

    //下发表格存储的路径
    private String uploadPath = "";

    //给用户下载的模板存储的路径
    private String downloadPath = "";

    private Map<String, String> nameCodeMap = null;

    private YfbPayService yfbPayService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("xiafa:list")
    public String list(Model model, @PageAttribute Page<Xiafa> page, HttpServletRequest request) {
        //得到此用户拥有的商户id
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        Params p = page.getParams();
        //根据时间查询
        String startDate = null;
        if (p != null) {
            startDate = p.containsKey("startDate") ? p.getString("startDate") : null;
        } else {
            p = new Params();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (org.apache.commons.lang3.StringUtils.isBlank(startDate)) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 5);
            startDate = sdf.format(c.getTime());
            p.put("startDate", startDate);
        }
        page.setParams(p);

        Page<Xiafa> xiafaPage = null;
        //如果拥有超级管理员权限
        if (roleIds.contains("1") || roleIds.contains("2")) {
            xiafaPage = xiafaService.findPage(page);
            model.addAttribute("page", xiafaPage);
            pac = page;
        } else if (StringUtil.isNoneBlank(merchantIds)) {
            //如果不是超级管理员则只查询个人交易记录信息
            String[] merchantIdArr = merchantIds.split(",");
            Params params = page.getParams();
            params.put("merchantId", merchantIdArr[0]);
            page.setParams(params);

            xiafaPage = xiafaService.findPage(page);
            model.addAttribute("page", xiafaPage);

            pac = page;
        }
        //将下发记录id集合保存到域中
        List<Xiafa> xiafaList = xiafaPage.getList();
        StringBuilder sb = new StringBuilder();
        if (xiafaList != null && xiafaList.size() > 0) {
            for (Xiafa xiafa : xiafaList) {
                sb.append(xiafa.getId()).append(",");
            }
            model.addAttribute("ids", sb.toString());
        }

        return "xiafa/xiafaList";
    }

    @RequestMapping(value = "up", method = RequestMethod.GET)
    @RequiresPermissions("xiafa:up")
    public String up(HttpServletRequest request) {
        //1.获取上传目录路径
        uploadPath = request.getSession().getServletContext().getRealPath("/upload") + "/";
        //2.获取上传目录路径
        downloadPath = request.getSession().getServletContext().getRealPath("/download") + "/";
        //3.初始化填充银行数据
        if (nameCodeMap == null) {
            initBankMap();
        }
        return "xiafa/xiafaUp";
    }

    /**
     * 从execl文件中读取银行数据
     */
    private void initBankMap() {
        //从银行码表中读出银行名称键值对
        Map<String, List<List<String>>> bankMap = POIExcelUtil.read(uploadPath + "银行.xlsx");
        List<List<String>> bankList = bankMap.get("Sheet1");
        nameCodeMap = new HashMap<>();
        if (bankList != null && bankList.size() > 0) {
            for (int i = 1; i < bankList.size(); i++) {
                List<String> row = bankList.get(i);
                nameCodeMap.put(row.get(0), row.get(1));
            }
        }
    }

    /**
     * 单记录下发
     *
     * @param id 下发Id
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    @RequiresPermissions("xiafa:up")
    public String pay(@RequestParam Long id, Model model) {
        BatchWithdrawData batchWithdrawData = new BatchWithdrawData();
        try {
            Xiafa xiafa = xiafaService.get(id.toString());
            //还未下发或者下发失败,才能进行下发操作
            if (0 == xiafa.getStatus() || 3 == xiafa.getStatus()) {
                if (xiafa.getRequest() == 0) {
                    String secretKey = merchantService.get(xiafa.getMerchantId().toString()).getSecretKey();
                    System.out.println(secretKey);
                    String rsp = batchWithdrawData.batchWithDraw(PayrollConstants.YYF_YSB_PF_URL, secretKey, xiafa);
                    //状态改为：下发中
                    xiafa.setStatus(1);
                    xiafa.setRequest(1);
                    xiafaService.update(xiafa);
                    logger.info("批量出款responseData:" + rsp);
                    AjaxResponse ajaxResponse = (AjaxResponse) JSON.parse(rsp);
                    //如果下发请求失败,添加错误信息
                    if (ajaxResponse.getCode() != 1) {
                        model.addAttribute("msg", ajaxResponse.getMsg());
                        return "http:/xiafa/list";
                    }

                } else {
                    model.addAttribute("msg", "有已请求过的下发!");
                    System.out.println("其中有已请求过的下发");
                }

            }
        } catch (Exception e) {
            System.out.println("调用批量出款出错：" + e);
            return "redirect:/xiafa/list";
        }
        return "redirect:/xiafa/list";
    }

    /**
     * 批量下发
     *
     * @param ids 下发id集合
     * @return
     */
    @RequestMapping(value = "/payMany", method = RequestMethod.GET)
    @RequiresPermissions("xiafa:up")
    public String payMany(@RequestParam String ids, Model model) {
        BatchWithdrawData batchWithdrawData = new BatchWithdrawData();
        try {
            if (StringUtils.isEmpty(ids)) {
                return "";
            }
            String[] idsArr = ids.split(",");
            for (String id : idsArr) {
                Xiafa xiafa = xiafaService.get(id);
                //还未下发或者下发失败,才能进行下发操作
                if (0 == xiafa.getStatus() || 3 == xiafa.getStatus()) {
                    if (0 == xiafa.getRequest()) {
                        String secretKey = merchantService.get(xiafa.getMerchantId().toString()).getSecretKey();
                        System.out.println(secretKey);
                        //状态改为：下发中
                        xiafa.setStatus(1);
                        //请求状态改为已请求
                        xiafa.setRequest(1);
                        xiafaService.update(xiafa);
                        String responseData = batchWithdrawData.batchWithDraw(PayrollConstants.YYF_YSB_PF_URL, secretKey, xiafa);
                        System.out.println("批量出款responseData:" + responseData);
                    } else {
                        model.addAttribute("msg", "有已请求过的下发!");
                        System.out.println("其中有已请求过的下发");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("调用批量出款出错：" + e);
            return "redirect:/xiafa/list";
        }
        return "redirect:/xiafa/list";
    }

    /**
     * 文件上传，使用SpringMVC提供的文件上传
     *
     * @param request   请求域对象
     * @param execlFile 表示上传的文件
     *                  注意：参数imgFile要与页面的表单元素名称一致，<input type="file" name="imgFile">
     */
    @RequestMapping("/upload")
    @RequiresPermissions("xiafa:up")
    public String upload(HttpServletRequest request,
                         @RequestParam("execlFile") MultipartFile execlFile,
                         @RequestParam("code") String userCode,
                         Model model) throws Exception {
        //获得发送给用户的验证码进行校验
        String smsCode = (String) request.getSession().getAttribute("code");
        if (smsCode == null || !smsCode.equals(userCode)) {
            //todo
            model.addAttribute("msg", "验证码为空或不正确");
            return "/xiafa/xiafaUp";
        }
        //验证成功以后让验证码失效
        request.getSession().removeAttribute("code");

        String merchantIds = LoginHelper.getMerchantIds();
        //如果该用户没有商户号，返回错误提示
        if (StringUtils.isEmpty(merchantIds)) {
            //todo
            model.addAttribute("msg", "用户没有商户号");
            return "/xiafa/xiafaUp";
        }

        //2.每天生成一个当天日期的目录:/upload/2018-09-23用于保存用户上传的下发表格
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //3. 创建目录对象
        File file = new File(uploadPath, now);
        if (!file.exists()) {
            file.mkdirs();
        }

        //4. 文件上传
        //4.1 获取文件名称
        if (execlFile == null) {
            model.addAttribute("msg", "你没有上传文件");
            return "/xiafa/xiafaUp";
        }
        String fileName = execlFile.getOriginalFilename();
        //4.2 文件名唯一
        fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName;
        //4.3 上传
        execlFile.transferTo(new File(file, fileName));
        if (!saveFile2db(now + "/" + fileName)) {
            model.addAttribute("msg", "你没有上传文件");
            return "/xiafa/xiafaUp";
        }
        return "redirect:/xiafa/list";
    }

    @Autowired
    private SmsUtil smsUtil;

    /**
     * 发送短信验证码
     *
     * @param model 用于将数据验证码存储在session域中
     * @return 响应数据
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.GET)
    @RequiresPermissions("xiafa:up")
    public @ResponseBody
    AjaxResponse sendSmsMsg(Model model) {
        String merchantIds = LoginHelper.getMerchantIds();
        //如果该用户没有商户号，返回错误提示
        if (StringUtils.isEmpty(merchantIds)) {
            //todo
            model.addAttribute("msg", "用户没有商户号");
        }
        //生成一个随机六位验证码
        String code = "";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        code = sb.toString();
        System.out.println("短信验证码:" + code);
        model.addAttribute("code", code);

        Merchant merchant = merchantService.get(merchantIds);
        String tel = merchant.getTel();
        Map<String, String> map = new HashMap<>();
        map.put("mobile", tel);
        map.put("signName", "Tosdom的私人小窝");
        map.put("templateCode", "SMS_153790688");
        map.put("templateParam", "{\"code\":\"" + code + "\"}");
        SendSmsResponse response = null;
        try {
            response = smsUtil.sendSms(
                    map.get("mobile"),
                    map.get("signName"),
                    map.get("templateCode"),
                    map.get("templateParam"));
        } catch (ClientException e) {
            e.printStackTrace();
            logger.warn("发送短信失败!");
            return AjaxResponse.failure("发送验证码失败");
        }
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
        return AjaxResponse.success("短信发送成功,请留意手机");
    }


    /**
     * 将execl中的数据录入进数据库
     *
     * @param pathAndName 存储在服务器端的文件的相对路径
     */
    private Boolean saveFile2db(String pathAndName) {
        String merchantIds = LoginHelper.getMerchantIds();
        String[] merchantIdsArr = merchantIds.split(",");
        //从下发表格中读出下发信息
        Map<String, List<List<String>>> cardMap = POIExcelUtil.read(uploadPath + pathAndName);
        List<List<String>> cardList = cardMap.get("Sheet1");
        if (cardList == null || cardList.size() == 0) {
            return false;
        }
        Long index = new Double((Math.random() + 1) * Math.pow(10, 9)).longValue();
        //读出下发表格的数据后存储数据库
        for (int i = 1; i < cardList.size(); i++) {

            System.out.println(cardList.size());
            List<String> row = cardList.get(i);
            Calendar calendar = Calendar.getInstance();
            String tradeNo = String.format("%s%s", DateUtil.dateToStr(calendar.getTime(), DateUtil.YMdhmsS_noSpli), RandomStringUtils.randomNumeric(15));
            System.out.println("tradeNo:" + tradeNo);
            Xiafa xiafa = new Xiafa();
            xiafa.setTradeNo(tradeNo);
            xiafa.setMerchantId(Long.valueOf(merchantIdsArr[0]));
            xiafa.setStatus(0);
            xiafa.setReceiverName(row.get(1));
            xiafa.setReceiverCardNo(row.get(2));
            xiafa.setBankName(row.get(3));
            xiafa.setCreateTime(new Date());
            xiafa.setBankCode(nameCodeMap.get(row.get(3)));
            String s = row.get(4).trim();
            BigDecimal amount = new BigDecimal(s);
            xiafa.setAmount(amount);
            xiafa.setBatch(index);
            xiafa.setRequest(0);
            xiafaService.save(xiafa);
        }
        return true;
        //System.out.println("下发列表:--------------------");
        //xiafalist.forEach(System.out::println);
    }


}
