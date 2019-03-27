package co.b4pay.admin.common.util.myutil.file;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2017-12-06
 * Time: 17:11
 */
public class FileUploadUtil {

    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
//    public static String uploadWithUuidName(MultipartFile file, String path) {
//        String newName = CommonUtil.createNewUuidName(file);
//        try {
//            if(CommonUtil.judeDirExists(new File(path))){
//                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(
//                        path, newName));
//                return newName;
//            }else {
//                return null;
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
