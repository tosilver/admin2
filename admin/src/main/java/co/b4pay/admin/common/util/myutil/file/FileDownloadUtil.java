package co.b4pay.admin.common.util.myutil.file;


import co.b4pay.admin.common.util.myutil.bind.Constants;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2017-12-06
 * Time: 14:32
 */
public class FileDownloadUtil {

    /**
     * 下载远程文件到指定目录（本地）
     *
     * @param urlString 远程地址
     * @param filename  文件名
     * @param savePath  保存路径
     */
    public static void downloadToLocalPath(String urlString, String filename, String savePath)
            throws IOException {
        InputStream inputStream = getInputStreamByUrl(urlString);
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (CommonUtil.judeDirExists(sf)) {
            OutputStream outputStream = new FileOutputStream(sf.getPath() + Constants.File.FILE_SEPARATOR + filename);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
            //读取完毕，关闭所有链接
            outputStream.close();
        }
        inputStream.close();
    }

    /**
     * 远程地址 获取 InputStream
     *
     * @param path
     * @return
     */
    public static InputStream getInputStreamByUrl(String path)
            throws IOException {
        InputStream is = null;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  //利用HttpURLConnection对象,我们可以从网络中获取网页数据.
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream(); //得到网络返回的输入流
    }

    /**
     * 根据二进制生成指定文件名的的文件直接下载到客户端
     *
     * @param fileName
     * @param bytes
     * @param request
     * @param response
     */
    public static void downloadToClient(String fileName, byte[] bytes,
                                        HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.reset();
        response.setContentType(getContentType(fileName));
        // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
        response.addHeader("Content-Disposition", "attachment;filename=\"" + encodeFilename(request, fileName) + "\"");//名称两边的双引号不能省略 兼容火狐 文件名中的空格
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }


    /**
     * 下载文件到客户端
     *
     * @param request
     * @param response
     */
    public static void downloadToClient(String filePath,
                                        HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        downloadToClient(fileName, bytes, request, response);
    }

    /**
     * 处理下载文件名，防止乱码
     *
     * @param request
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String encodeFilename(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");
        try {
            if (null != agent && agent.contains("MSIE")) {
                // IE
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && agent.contains("Mozilla")) {
                // Firefox
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            try {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return fileName;
    }


    /**
     * 根据文件后缀名获取下载类型
     *
     * @param fileName
     * @return
     */
    private static String getContentType(String fileName) {
        String ext = CommonUtil.getPostfix(fileName);
        if (ext.equals(".zip")) {
            return "application/zip";
        } else if (ext.equals(".xls") || ext.equals(".xlsx")) {
            return "application/x-excel";
        } else if (ext.equals(".doc") || ext.equals(".docx")) {
            return "application/msword";
        } else if (ext.equals(".pdf")) {
            return "application/pdf";
        } else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
            return "image/jpeg";
        } else if (ext.equals(".gif")) {
            return "image/gif";
        } else if (ext.equals(".png")) {
            return "image/png";
        } else if (ext.equals(".bmp")) {
            return "image/bmp";
        }
        return "application/force-download";
    }


    /**
     * @param dirPath 文件将要保存的目录
     * @param method  请求方法，包括POST和GET
     * @param url     请求的路径
     * @return
     * @功能 下载远程文件到本地指定目录
     */
    public static File downloadToClientDir(String url, String dirPath, String fileName, String method)
            throws IOException {
        //创建不同的文件夹目录
        File file = new File(dirPath);
        file.deleteOnExit();
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        // 建立链接
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        //以Post方式提交表单，默认get方式
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        // post方式不能使用缓存
        conn.setUseCaches(false);
        //连接指定的资源
        conn.connect();
        //获取网络输入流
        InputStream inputStream = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        //判断文件的保存路径后面是否以/结尾
        if (!dirPath.endsWith(Constants.File.FILE_SEPARATOR)) {
            dirPath += Constants.File.FILE_SEPARATOR;
        }
        //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
        FileOutputStream fileOut = new FileOutputStream(dirPath + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fileOut);

        byte[] buf = new byte[1024];
        int length = bis.read(buf);
        //保存文件
        while (length != -1) {
            bos.write(buf, 0, length);
            length = bis.read(buf);
        }
        bos.close();
        bis.close();
        conn.disconnect();
        return file;
    }
}
