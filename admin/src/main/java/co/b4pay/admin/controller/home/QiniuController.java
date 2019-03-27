package co.b4pay.admin.controller.home;

import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.core.api.qiniu.QiniuApi;
import co.b4pay.admin.entity.base.AjaxResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 七牛相关接口<br>
 * Demo: http://jssdk.demo.qiniu.io/uptoken
 *
 * @author YK
 * @version $Id: QiniuController.java, v 0.1 2016年5月3日 下午22:36:35 YK Exp $
 */
@RestController
@RequestMapping("/api/qiniu")
public class QiniuController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(QiniuController.class);

    @Autowired
    private QiniuApi qiniuApi;

//    @RequestMapping(value = "/uptoken", method = RequestMethod.GET)
//    public AjaxResponse uptoken() {
//        String uptoken = qiniuApi.getPublicUptoken();
//        String domain = qiniuApi.getPublicDomain();
//        return AjaxResponse.success().add("uptoken", uptoken).add("domain", domain);
//    }

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public AjaxResponse upload(CommonsMultipartFile file) {
//        String suffix = getFileExtension(file.getOriginalFilename());
//        String fileName = String.valueOf(System.currentTimeMillis());
//        if (suffix != null) {
//            fileName += ("." + suffix.toLowerCase());
//        }
//        String key = qiniuApi.uploadToPublic(file.getBytes(), fileName);
//        if (StrKit.notBlank(key)) {
//            if (logger.isInfoEnabled()) {
//                logger.info("[" + file.getOriginalFilename() + "]上传成功！" + key);
//            }
//            return AjaxResponse.success("key", key);
//        } else {
//            return AjaxResponse.failure("上传失败");
//        }
//
//    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public AjaxResponse delete(String key) {
        qiniuApi.deletePublic(key);
        return AjaxResponse.success();
    }

    private String getFileExtension(String filename) {
        if (StringUtils.isBlank(filename)) {
            return null;
        }
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        return filename.substring(index + 1);
    }

}
