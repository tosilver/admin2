package co.b4pay.admin.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    private static final CloseableHttpClient httpClient;
    private static Logger logger = (Logger) LoggerFactory.getLogger(HttpUtils.class);

    public static String CHARSET_UTF8 = "UTF-8";

    public static String PROP_CLASS = "class";

    public static final int SOCKET_TIMEOUT = 15000;

    public static final int CONNECT_TIMEOUT = 60000;

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT).build();// 设置请求和传输超时时间

    static {
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET_UTF8);
    }

//	public static String doPost(String url, Map<String, String> params) {
//		return doPost(url, params, CHARSET_UTF8);
//	}
//    

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            System.out.println("请求URL参数:" + url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("doGet error!", e);
        }
        return null;
    }

    /**
     * post form 请求
     *
     * @param urlString      请求地址
     * @param nameValuePairs 请求参数
     * @return
     * @throws IOException
     */
    public static String doPost(String urlString, Map<String, String> nameValuePairs)
            throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            boolean first = true;
            for (Map.Entry<String, String> pair : nameValuePairs.entrySet()) {
                if (first)
                    first = false;
                else
                    out.print('&');
                String name = pair.getKey().toString();
                String value = pair.getValue().toString();
                out.print(name);
                out.print('=');
                out.print(URLEncoder.encode(value, "UTF-8"));
            }
        }
        StringBuffer response = new StringBuffer("");
        try (Scanner in = new Scanner(connection.getInputStream())) {
            while (in.hasNextLine()) {
                String str = new String(in.nextLine().getBytes(), "UTF-8");
                response.append(str);
                response.append("\n");
            }
        } catch (IOException e) {
            if (!(connection instanceof HttpURLConnection))
                throw e;
            InputStream err = ((HttpURLConnection) connection).getErrorStream();
            if (err == null)
                throw e;
            Scanner in = new Scanner(err);
            String str = new String(in.nextLine().getBytes(), "UTF-8");
            response.append(str);
            response.append("\n");
            in.close();
        }
        return response.toString();
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET_UTF8));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("doPost error!", e);
        }
        return null;
    }

    /**
     * 通过指定参数名的方式POST
     *
     * @param paramMap MAP键值对参数
     */
    public static String postSend(String url, Map<String, String> paramMap) {
        return postSend(url, paramMap, CHARSET_UTF8);
    }

    /**
     * 通过指定参数名的方式POST
     *
     * @param paramMap MAP键值对参数
     * @param 字条编码
     */
    public static String postSend(String url, Map<String, String> paramMap, String charset) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> formParams = getNameValuePair(paramMap);
            UrlEncodedFormEntity entity = null;
            entity = new UrlEncodedFormEntity(formParams, null == charset ? CHARSET_UTF8 : charset);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            InputStream resStream = resEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf8"));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("postSend error!", e);
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 通过指定参数名的方式POST(默认值为空属性也传递)
     *
     * @param obj PO对象属性值对
     */
    public static String postSend(String url, Object obj) {
        return postSend(url, obj, CHARSET_UTF8, true);
    }

    public static String postSend(String url, Object obj, String charset, boolean isSendEmpty) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> formParams = getNameValuePair(obj, isSendEmpty);
            UrlEncodedFormEntity entity = null;
            entity = new UrlEncodedFormEntity(formParams, charset);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            InputStream resStream = resEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf8"));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();
        } catch (Exception e) {
            logger.error("postSend error!", e);
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 不指定参数名的方式来POST数据
     *
     * @param content 内容
     */
    public static String postContent(String url, String content, String charset) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            StringEntity entity = new StringEntity(content, charset);
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            InputStream resStream = resEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf8"));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();
        } catch (Exception e) {
            logger.error("postContent error!", e);
            e.printStackTrace();
        }
        return result;
    }

    public static String httpget(final String urlstr, final Map<String, Object> params) {
        final StringBuilder sb = new StringBuilder();
        try {
            final URL url = new URL(urlstr + "?" + createParams(params));
            final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(urlConn.getInputStream(), CHARSET_UTF8));
            String s = null;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("httpget error!", e);
        }
        return sb.toString();
    }

    /**
     * post提交+文件上传
     *
     * @param urlstr   接口地址
     * @param obj      实体对象
     * @param data     文件byte[]
     * @param filename 文件名
     * @param name     属性名
     * @return
     * @throws Exception
     */
    public static String httpMultiPost(final String urlstr, final Object obj, byte[] data, String filename,
                                       String name) {
        StringBuffer rs = new StringBuffer();
        try {
            String BOUNDARY = "---------7d4a6d158c9";
            @SuppressWarnings("unchecked")
            Map<String, Object> map = PropertyUtils.describe(obj);
            String u = urlstr + "?" + createParams(map);
            URL url;
            url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 定义最后数据分隔线
            if (data != null && data.length > 0) {
                byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"" + name + "\";filename=\"" + filename + "\"\r\n");
                sb.append("Content-Type:application/octet-stream\r\n\r\n");
                byte[] start_data = sb.toString().getBytes();
                out.write(start_data);
                out.write(data);
                out.write("\r\n".getBytes());
                out.write(end_data);
            }
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                rs.append(line);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("httpMultiPost error!", e);
            e.printStackTrace();
        }
        return rs.toString();
    }

    /**
     * 将POJO转为参数
     */
    private static List<NameValuePair> getNameValuePair(Object obj, boolean isSendEmpty) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = PropertyUtils.describe(obj);
            Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Object> parmEntry = iter.next();
                String key = parmEntry.getKey();
                String value = null == parmEntry.getValue() ? null : String.valueOf(parmEntry.getValue());
                if (PROP_CLASS.equalsIgnoreCase(key)) {
                    continue;
                }
                if (!isSendEmpty && null == value) {
                    continue;
                }
                paramList.add(new BasicNameValuePair(key, value));
            }
        } catch (Exception e) {
            logger.error("getNameValuePair error!", e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return paramList;
    }

    private static List<NameValuePair> getNameValuePair(Map<String, String> map) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> parmEntry = iter.next();
            paramList.add(new BasicNameValuePair(parmEntry.getKey(), parmEntry.getValue()));
        }
        return paramList;
    }

    public static String createParams(final Map<String, Object> params) {
        final StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            int cnt = 0;
            for (String key : params.keySet()) {
                final Object val = params.get(key);
                sb.append(urlEncode(key));
                sb.append("=");
                if (val != null && !val.toString().isEmpty())
                    sb.append(urlEncode(val.toString()));
                if (cnt++ != params.keySet().size() - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    private static String urlEncode(final String text) {
        String res = null;
        try {
            if (text != null) {
                res = URLEncoder.encode(text, CHARSET_UTF8);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * POST方式发送名值对请求URL,上传文件（包括图片）
     *
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return
     */
    public static String postEntity(String url, Object pojo) {
        return postEntity(url, getPojo2Map(pojo, false));
    }

    public static byte[] postImportEntity(String url, Object pojo) {
        return postImportEntity(url, getPojo2Map(pojo, false));
    }


    /**
     * 将POJO转为Map参数
     */
    private static Map<String, Object> getPojo2Map(Object obj, boolean isSendEmpty) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = PropertyUtils.describe(obj);
            Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Object> parmEntry = iter.next();
                String key = parmEntry.getKey();
                Object value = parmEntry.getValue();
                if (PROP_CLASS.equalsIgnoreCase(key)) {
                    continue;
                }
                if (!isSendEmpty && null == value) {
                    continue;
                }
                paramMap.put(key, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return paramMap;
    }

    public static String postMethod(String content, String addr, String encoding) throws IOException {

        StringBuilder rtn = new StringBuilder();
        OutputStream os = null;
        PrintWriter pw = null;
        BufferedReader in = null;
        try {

            URL url = new URL(addr);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();

            uc.setConnectTimeout(2000); // 连接超时设置为2s
            uc.setReadTimeout(20000); // 读超时设置为20s
            uc.setDoOutput(true); // 因为这个是post请求，参数要放在http正文内，因此需要设为true，默认情况下是false
            uc.setUseCaches(false); // post请求不能使用缓存
            // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            uc.setRequestProperty("Content-type", "text/xml;charset=utf-8");
            uc.setRequestMethod("POST"); // 设定请求的方法为"POST"，默认是GET
            uc.connect();

            os = uc.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(os, "utf-8");
            pw = new PrintWriter(out);
            pw.print(content);
            pw.flush();

            /* 获取服务器端返回信息 */
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), encoding));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                rtn.append(inputLine).append("\r\n");
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "time_out";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                    pw = null;
                }
                if (os != null) {
                    os.close();
                    os = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rtn.toString();
    }


    public static String httpRequest(String requestUrl, String requestMethod,
                                     String outputStr) {
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            // 往服务器端写内容 也就是发起http请求需要带的参数
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            File file = new File("xxx.html");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("服务器端创建文件失败");
                }
            } else {
                /** 在此也可以询问是否覆盖 */
                System.out.println("本路径已存在相同文件，进行覆盖");
            }
            /** 将文件包装到文件输出流对象中 */
            FileOutputStream fos = new FileOutputStream(file);
            /** size为每次接收数据包的长度 */
            int size = 0;
            /** count用来记录已接收到文件的长度 */
            long count = 0;
            byte[] b = new byte[1024];
            while (is.read(b) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {
            System.out.println("服务器端创建文件失败");
        }
        return File.pathSeparator;
    }


    public static void main(String[] args) {
        String s = httpRequest("https://mp.weixin.qq.com/s/CluiFUMe4p_B0Vg4BQrm5g", "GET", null);
        //System.out.println(s);
    }
}
