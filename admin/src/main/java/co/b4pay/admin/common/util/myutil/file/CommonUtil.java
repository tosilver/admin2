package co.b4pay.admin.common.util.myutil.file;


import co.b4pay.admin.common.util.myutil.bind.Constants;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件操作公用工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-02-07
 * Time: 15:25
 */
public class CommonUtil {

    /**
     * 获取文件名后缀
     *
     * @param fileName
     * @return
     */
    public static String getPostfix(String fileName) {
        return FilenameUtils.getExtension(fileName);

    }

    /**
     * 创建对应文件
     *
     * @param file
     * @return
     */
    public static boolean judeDirExists(File file) {
        boolean flag = false;
        if (file.exists()) {
            if (file.isDirectory()) {
                flag = true;
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            flag = file.mkdirs();
        }
        return flag;
    }

    /**
     * 通过UUID生成唯一文件名
     *
     * @param file
     * @return
     */
    public static String createNewUuidName(MultipartFile file) {
        return UUID.randomUUID().toString() + Constants.File.FILE_POINT + getPostfix(file.getOriginalFilename());
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getBytesOfFile(String filePath)
            throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        return bos.toByteArray();
    }


    /**
     * 根据byte数组，生成文件
     *
     * @param bfile
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void getFileOfByte(byte[] bfile, String filePath, String fileName)
            throws IOException {
        File dir = new File(filePath);
        judeDirExists(dir);
        File file = new File(filePath + Constants.File.FILE_SEPARATOR + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(bfile);
        bos.close();
        fos.close();
    }
}
