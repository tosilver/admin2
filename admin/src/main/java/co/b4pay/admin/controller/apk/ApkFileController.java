package co.b4pay.admin.controller.apk;


import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.ApkFile;


import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;

import co.b4pay.admin.entity.base.ResponseMode;
import co.b4pay.admin.service.ApkFileService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("apk")
public class ApkFileController extends BaseController {
    private static final String APK_P = System.getProperty("file.separator");

    /**
     * 文件上传功能
     *
     * @param file
     * @return
     * @throws IOException
     */

    @Autowired
    ApkFileService apkFileService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("apk:list")
    public String list(Model model, @PageAttribute Page<ApkFile> page) {
        Page<ApkFile> page1 = apkFileService.findPage(page);
        model.addAttribute("page", apkFileService.findPage(page));

        return "apk/apkList";

    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @RequiresPermissions("apk:save")
    public String form() {
        return "apk/apkUpload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @RequiresPermissions("apk:upload")
    public String upload(MultipartFile aplFile, HttpServletRequest request, Model model) throws IOException {

        //String path = request.getSession().getServletContext().getRealPath(APK_P+"apkFile");
        String path = APK_P + "var" + APK_P + "data" + APK_P + "apkFile";
        String fileName = aplFile.getOriginalFilename();
        File dir = new File(path + APK_P + fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        String name = request.getParameter("name");
        String version = request.getParameter("version");
        ApkFile apkFile = new ApkFile();
        apkFile.setName(name);
        apkFile.setVersion(version);
        apkFile.setFileUrl(path + APK_P + fileName);
        apkFile.setStatus(1);
        apkFileService.save(apkFile);
        aplFile.transferTo(dir);

        return "redirect:list";
    }


    @RequestMapping(value = "form", method = RequestMethod.GET)
    @RequiresPermissions("apk:form")
    public String form(Model model, String id) {
        model.addAttribute("apkFile", apkFileService.get(id));
        return "apk/apkForm";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("apk:update")
    public String saveUpload(MultipartFile aplFile, HttpServletRequest request) throws IOException {
//        String path = request.getSession().getServletContext().getRealPath(APK_P+"apkFile");
        String path = APK_P + "var" + APK_P + "data" + APK_P + "apkFile";
        String fileName = aplFile.getOriginalFilename();
        File dir = new File(path + APK_P + fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        String id = request.getParameter("id");
        String version = request.getParameter("version");
        ApkFile apkFile = apkFileService.get(id);
        apkFile.setVersion(version);
        apkFile.setFileUrl(path + APK_P + fileName);
        apkFileService.update(apkFile);
        aplFile.transferTo(dir);
        return "redirect:list";
    }

    /**
     * 文件下载功能
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/down", method = RequestMethod.GET)
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //模拟文件，myfile.txt为需要下载的文件

        try {
            String id = request.getParameter("id");
            if (id != null && id != "") {
                ApkFile apkFile = apkFileService.get(id);
                if (apkFile != null) {
                    String fileUrl = apkFile.getFileUrl();
                    //String fileName = request.getSession().getServletContext().getRealPath("apkFile")+"/myfile.txt";
                    //获取输入流
                    File file = new File(fileUrl);
                    InputStream bis = new BufferedInputStream(new FileInputStream(file));
                    String[] urlname = fileUrl.replaceAll("\\\\", "/").split("/");
                    String nameurl = urlname[urlname.length - 1];
                    // String version = apkFile.getVersion();
                    nameurl = URLEncoder.encode(nameurl, "UTF-8");
                    //转码，免得文件名中文乱码
                    //设置文件下载头
                    response.addHeader("Content-Disposition", "attachment;filename=" + nameurl);
                    //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
                    // response.setContentType("multipart/form-data");
                    response.setContentType("application/vnd.android.package-archive");//设置response内容的类型 下载安卓应用apk
                    response.setContentLength((int) file.length());//设置文件大小
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    int len = 0;
                    while ((len = bis.read()) != -1) {
                        out.write(len);
                        out.flush();
                    }
                    out.close();
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().print("文件不存在");
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("缺少参数ID");
            }
        } catch (DtoException d) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("文件已删除");
        }

    }

    @RequestMapping(value = "/getVersion", method = RequestMethod.POST)
    public @ResponseBody
    ApkFile getVersion(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ApkFile apkFile = apkFileService.get(id);
        // String version = apkFile.getVersion();
        return apkFile;

    }
}
