package co.b4pay.admin.controller.scanLife;


import co.b4pay.admin.common.constants.Constants;
import co.b4pay.admin.common.helper.LoginHelper;

import co.b4pay.admin.common.util.HttpClientUtils;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.util.myutil.QRCodeUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("scanLife")
public class ScanLifeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ScanLifeController.class);

    /**
     * 二维码上传功能
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Autowired
    ScanLifeService scanLifeService;
    @Autowired
    RouterService routerService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    ChannelService channelService;
    @Autowired
    MerchantRateService merchantRateService;

    /**
     * 页面
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("scanLife:list")
    public String arbList(Model model, @PageAttribute Page<ScanLife> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();

        if (roleIds.contains("1")) {   //如果拥有超级管理员权限
            Params paramsc = page.getParams();
            List<Router> routerList = routerService.findList();
            model.addAttribute("routerList", routerList);
            model.addAttribute("page", scanLifeService.findPage(page));
            if (paramsc != null) {
                try {
                    String routerId = paramsc.getString("routerId");
                    if (routerId != null && routerId != "") {
                        List<Channel> channelList = channelService.findByPayCost(routerId);
                        model.addAttribute("channelList", channelList);
                    }

                } catch (DtoException e) {
                    model.addAttribute("channelList", channelService.findList());
                }

            } else {
                model.addAttribute("channelList", channelService.findList());
            }
            model.addAttribute("merchantList", merchantService.findList());


        } else if (StringUtil.isNoneBlank(merchantIds)) {    //如果不是超级管理员则只查询个人交易记录信息
            Params params = page.getParams();
            if (params != null) {
                try {
                    String routerId = params.getString("routerId");
                    if (routerId != null && routerId != "") {
                        List<Channel> channelList = channelService.findByPayCost(routerId);
                        List<MerchantRate> byMerchantId = merchantRateService.findByMerchantId(merchantIds);
                        List<Router> routerList = new ArrayList();
                        for (int i = 0; i < byMerchantId.size(); i++) {
                            //String id = byMerchantId.get(i).getRouter().getId();
                            routerList.add(byMerchantId.get(i).getRouter());
                        }
                        model.addAttribute("routerList", routerList);
                        model.addAttribute("channelList", channelList);
                    }
                } catch (DtoException D) {
                    List<MerchantRate> byMerchantId = merchantRateService.findByMerchantId(merchantIds);
                    List<Router> routerList = new ArrayList();
                    List channelList = new ArrayList();
                    for (int i = 0; i < byMerchantId.size(); i++) {
                        String id = byMerchantId.get(i).getRouter().getId();

                        routerList.add(byMerchantId.get(i).getRouter());

                        List<Channel> byPayCost = channelService.findByPayCost(id);
                        for (int j = 0; j < byPayCost.size(); j++) {

                            channelList.add(byPayCost.get(j));
                        }
                    }
                    model.addAttribute("routerList", routerList);
                    model.addAttribute("channelList", channelService.findList());
                }

            } else {
                List<MerchantRate> byMerchantId = merchantRateService.findByMerchantId(merchantIds);
                List<Router> routerList = new ArrayList();
                List channelList = new ArrayList();
                for (int i = 0; i < byMerchantId.size(); i++) {
                    String id = byMerchantId.get(i).getRouter().getId();

                    routerList.add(byMerchantId.get(i).getRouter());

                    List<Channel> byPayCost = channelService.findByPayCost(id);
                    for (int j = 0; j < byPayCost.size(); j++) {

                        channelList.add(byPayCost.get(j));
                    }
                }
                model.addAttribute("routerList", routerList);
                model.addAttribute("channelList", channelService.findList());
            }
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
                System.out.println(params);
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }
            page.setParams(params);


            model.addAttribute("page", scanLifeService.findPage(page));
            // model.addAttribute("routerList", routerList);
            //model.addAttribute("channelList",channelList);
        }

        return "scanLife/scanLifeList";
    }


    /**
     * 跳转上传页面
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @RequiresPermissions("scanLife:upload")
    public String form(Model model) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        if (roleIds.contains("1")) {   //如果拥有超级管理员权限

            model.addAttribute("merchantList", merchantService.findList());
        } else if (StringUtil.isNoneBlank(merchantIds)) {
            Merchant merchant = merchantService.get(merchantIds);
            List<Merchant> merchantList = new ArrayList<>();
            merchantList.add(merchant);
            model.addAttribute("merchantList", merchantList);
            System.out.println(merchantList.toString());
        }
        model.addAttribute("channelList", channelService.findList());
        model.addAttribute("routerList", routerService.findList());
        return "scanLife/scanLifeUpload";
    }

    /**
     * 跳转更新页面
     * @param model
     * @param id
     * @return
     */
//    @RequestMapping(value = "form",method = RequestMethod.GET)
//    @RequiresPermissions("scanLife:form")
//    public String form(Model model, String id) {
//        model.addAttribute("scanLife",scanLifeService.get(id));
//        return "scanLife/scanLifeForm";
//    }

    /**
     * 单个文件/批量文件上传
     *
     * @param request
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "getChannel", method = RequestMethod.GET)
    public @ResponseBody
    List getChannel(HttpServletRequest request) {
        String routerId = request.getParameter("routerId");
        List<Channel> byProduct = channelService.findByPayCost(routerId);
        return byProduct;

    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @RequiresPermissions("scanLife:upload")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        // List<MultipartFile> scanFile = request.getFiles("scanFile");
        String p = System.getProperty("file.separator");
        String label = request.getParameter("label");
        String scan = "";
        if (label.equals("1")) { //判断二维码类型 1：固额 2：任意
            scan = p + "fixedCode";
        } else {
            scan = p + "unFixedCode";
        }
        //String path = request.getSession().getServletContext().getRealPath(scan);
        String cspath = p + "var" + p + "data" + scan;//指定文件目录
        //String cspath = "C:"+p+"data"+scan;
        String merchantId = request.getParameter("merchantId");
        String channelId = request.getParameter("channelId");
        String channelName = channelService.get(channelId).getName();
        Integer product = channelService.get(channelId).getProduct();
        //拼接文件夹路径
        String pictureUrl = cspath + p + merchantId + p + product + p + channelName;
        String folderUrl = merchantId + request.getParameter("routerId") + channelId + label;
        scanLifeService.delete(folderUrl);
        logger.info("图片路径" + pictureUrl);
        /*
         * 再说回刚才的form, 假设我们在单个文件选框中上传了文件1, 多个文件选框中上传了文件2, 3, 4.
         * 那么对于后台接收到的, 可以这么理解, 就是一个Map的形式(实际上它后台真的是以Map来存储的).
         * 这个Map的Key是什么呢? 就是上面<input>标签中的name=""属性. Value则是我们刚才上传的
         * 文件, 通过下面的示例可以看出每一个Value就是一个包含对应文件集合的List
         *
         * 传到后台接收到的Map就是这样:
         * scanfile: 文件1
         * scanfileList: 文件2, 文件3, 文件4
         * 虽然从方法名的表面意义来看是得到文件名, 但实际上这个文件名跟上传的文件本身并没有什么关系.
         * 刚才说了这个Map的Key就是<input>标签中的name=""属性, 所以得到的也就是这个属性的值
         */
        Iterator<String> fileNames = request.getFileNames();

        while (fileNames.hasNext()) {
            //把fileNames集合中的值打出来
            String fileName = fileNames.next();
            /*
             * request.getFiles(fileName)方法即通过fileName这个Key, 得到对应的文件
             * 集合列表. 只是在这个Map中, 文件被包装成MultipartFile类型
             */
            List<MultipartFile> fileList = request.getFiles(fileName);
            if (fileList.size() > 0) {
                //遍历文件列表
                Iterator<MultipartFile> fileIte = fileList.iterator();
                while (fileIte.hasNext()) {
                    //获得每一个文件
                    MultipartFile multipartFile = fileIte.next();
                    //获得原文件名
                    String originalFilename = multipartFile.getOriginalFilename();
                    System.out.println("originalFilename: " + originalFilename);
                    //检查该路径对应的目录是否存在. 如果不存在则创建目录
                    File dir = new File(pictureUrl);

                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String filePath = pictureUrl + p + originalFilename;
                    System.out.println("filePath: " + filePath);
                    //保存文件
                    File dest = new File(filePath);

                    if (!(dest.exists())) {
                        dest.setWritable(true, false);
                        multipartFile.transferTo(dest);//将获取到的文件以File形式传输至指定路径.
                        toBean(request, filePath, originalFilename, dest, folderUrl);//保存文件信息到数据库

                        /*
                         * 如果需对文件进行其他操作, MultipartFile也提供了
                         * InputStream getInputStream()方法获取文件的输入流
                         *
                         * 例如下面的语句即为通过
                         * org.apache.commons.io.FileUtils提供的
                         * void copyInputStreamToFile(InputStream source, File destination)
                         * 方法, 获取输入流后将其保存至指定路径
                         */
                        //FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dest);
                    }
                    //MultipartFile也提供了其他一些方法, 用来获取文件的部分属性
                    //获取文件contentType
//                    String contentType=multipartFile.getContentType();
//                    System.out.println("contentType: "+contentType);
//                    String name=multipartFile.getName();
//                    System.out.println("name: "+name);
//                    //获取文件大小, 单位为字节
//                    long size=multipartFile.getSize();
//                    System.out.println("size: "+size);
//                    System.out.println("---------------------------------------------------");
                }
            }
        }
        // HttpClientUtils.httpGet(Constants.SCAN_LIFE);

        return "redirect:list";
    }

//    /**
//     * 上传文件并压缩处理
//     * @param request
//     * @param params
//     * @param values
//     * @return
//     * @throws Exception
//     */
//
//    public static List<Map<String,Object>> upload(HttpServletRequest request,
//                                                  String[] params,Map<String,Object[]> values) throws Exception{
//      List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
//        MultipartHttpServletRequest mRequest =(MultipartHttpServletRequest) request;
//        Map<String,MultipartFile> fileMap = mRequest.getFileMap();
//        String realPath = request.getSession().getServletContext().getRealPath("/");
//        File file = new File(realPath);
//        if (!file.exists()){
//            file.mkdir();
//        }
//        String fileName =null;
//        int i = 0;
//        for (Iterator<Map.Entry<String,MultipartFile>> it =  fileMap.entrySet().iterator();it.hasNext();i++){
//            Map.Entry<String,MultipartFile> entry = it.next();
//            MultipartFile mFile = entry.getValue();
//            String filename = mFile.getOriginalFilename();
//            String storeName = rename(fileName);
//            String noZipName = realPath+storeName;
//            String zipName = zipName(noZipName);
//            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipName)));
//            outputStream.putNextEntry(new ZipEntry(fileName));
//        }
//    return null;
//    }

    /**
     * 保存二维码信息
     *
     * @param request
     * @param pictureUrl
     */

    private void toBean(HttpServletRequest request, String pictureUrl, String originalFilename, File dest, String folderUrl) {
        if (originalFilename != null && originalFilename != "") {
            final String codeName = StringUtil.substringBeforeLast(originalFilename, ".");
            try {
                String decode = QRCodeUtil.decode(dest);
                ScanLife scanLife = new ScanLife();
                scanLife.setName(codeName);
                scanLife.setMerchant(merchantService.get(request.getParameter("merchantId")));
                scanLife.setRouter(routerService.get(request.getParameter("routerId")));
                scanLife.setChannel(channelService.get(request.getParameter("channelId")));
                scanLife.setLabel(Integer.parseInt(request.getParameter("label")));
                scanLife.setPictureUrl(pictureUrl);
                scanLife.setNetworkUrl(decode);
                scanLife.setFolderUrl(folderUrl);
                scanLife.setStatus(1);
                scanLifeService.save(scanLife);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

//    public static void main(String[] args) throws NotFoundException {
//        MultiFormatReader formatReader=new MultiFormatReader();
//        File file =new File("D:/63792632718042569.jpg");
//        BufferedImage image=null;
//        try {
//            image = ImageIO.read(file);
//        } catch (IOException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        BinaryBitmap binaryBitmap =new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
//        Hashtable hints=new Hashtable();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        Result result=formatReader.decode(binaryBitmap,hints);
//        System.err.println("解析结果："+result.toString());
//        System.out.println(result.getBarcodeFormat());
//        System.out.println(result.getText());
//
//
//    }
    /**
     * 重命名文件
     * @param name
     * @return
    //     */
//    private static String rename(String name){
//        long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//        Random random = new Random(System.currentTimeMillis());
//        String fileName =now+""+random;
//        if (name.indexOf(".") != -1){
//            fileName +=name.substring(name.lastIndexOf("."));
//        }
//        return fileName;
//
//    }

    /**
     * 压缩后的文件名
     * @param name
     * @return
     */
//    private static String zipName(String name) throws IOException{
//        String prefix="";
//        if (name.indexOf(".") !=-1){
//           prefix= name.substring(0,name.lastIndexOf("."));
//        }else {
//            prefix=name;
//        }
//        return  prefix+".zip";
//    }

}


