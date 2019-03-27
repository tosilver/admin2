package co.b4pay.admin.common.util.myutil.file;

import co.b4pay.admin.common.util.myutil.bind.Constants;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip相关工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2017-12-19
 * Time: 17:32
 */
public class ZipUtil {

    //压缩
    public static int k = 1; // 定义递归次数变量

    public static void compress(File inputFile, String zipFileName) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        compress(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close(); // 输出流关闭
    }

    public static void compress(ZipOutputStream out, File f, String base,
                                BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl == null || fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + Constants.File.FILE_SEPARATOR)); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                compress(out, fl[i], base + Constants.File.FILE_SEPARATOR + fl[i].getName(), bo); // 递归遍历子文件夹
            }
            System.out.println("第" + k + "次递归");
            k++;
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            //System.out.println(base);
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }

    //解压一级
    public static List<String> decompression(File inputZipFile, String filePath) throws IOException {
        List<String> fileNames = new ArrayList<>();
        ZipInputStream Zin = new ZipInputStream(new FileInputStream(inputZipFile));//输入源zip路径
        BufferedInputStream Bin = new BufferedInputStream(Zin);
        File Fout = null;
        ZipEntry entry;
        while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
            //获取文件
            Fout = new File(filePath, entry.getName());
            if (!Fout.exists()) {
                (new File(Fout.getParent())).mkdirs();
            }
            FileOutputStream out = new FileOutputStream(Fout);
            BufferedOutputStream Bout = new BufferedOutputStream(out);
            int b;
            while ((b = Bin.read()) != -1) {
                Bout.write(b);
            }
            Bout.close();
            out.close();
            fileNames.add(entry.getName());
        }
        Bin.close();
        Zin.close();
        return fileNames;
    }

    /**
     * @param descDir 解压出来的文件保存的目录
     * @throws IOException
     * @description：解压文件操作多级目录 zip文件路径
     */
    public static List<String> unZipFiles(File zipFile, String descDir) throws IOException {
        List<String> fileNames = new ArrayList<>();
        ZipInputStream zip = null;
        BufferedInputStream bin = null;
        FileOutputStream out = null;
        File file = null;
        ZipEntry entry = null;
        BufferedOutputStream bout = null;
        zip = new ZipInputStream(new FileInputStream(zipFile));//输入源zip路径
        bin = new BufferedInputStream(zip);

        while ((entry = zip.getNextEntry()) != null) {
            String zipEntryName = entry.getName();
            String outPath = (descDir + "" + zipEntryName).replaceAll("\\*", Constants.File.FILE_SEPARATOR);
            // 判断路径是否存在,不存在则创建文件路径
            file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) file.mkdirs();
            // 判断文件全路径是否为文件夹,如果是上面已经创建,不需要解压
            if (new File(outPath).isDirectory()) continue;
            file = new File(descDir, entry.getName());
            if (!file.exists()) {
                (new File(file.getParent())).mkdirs();
            }
            out = new FileOutputStream(file);
            bout = new BufferedOutputStream(out);

            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
            bout.close();
            fileNames.add(zipEntryName);
        }
        zip.close();
        bin.close();
        if (out != null) {
            out.close();
        }
        if (bout != null) {
            bout.close();
        }
        return fileNames;
    }

}
