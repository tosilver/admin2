package co.b4pay.admin.common.core.api.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

public class Client {

    /**
     * http post方式提交
     */
    private String HttpURL = "http://www.jc-chn.cn/";
//    private String HttpURL  = "http://sms-cly.cn/";

    private String userName = "";
    private String password = "";
    private String pwd = "";

    Client() {
    }

    ;

    /**
     * 构造函数
     *
     * @param username
     * @param password
     * @throws UnsupportedEncodingException
     */
    public Client(String username, String password) throws UnsupportedEncodingException {
        this.userName = username;
        this.password = password;
        this.pwd = MD5Utils.MD5Encode(username + MD5Utils.MD5Encode(password));
    }

    /**
     * 方法名称：getBalance 功 能：获取余额 参 数：无 返 回 值：余额（String）
     */
    public String getBalance() {
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder params = new StringBuilder();
        params.append("username=").append(userName).append("&password=").append(this.getPwd());
        try {
            URL realUrl = new URL(HttpURL + "balanceQuery.do");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
            out.write(params.toString());
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 方法名称：mt 功 能：发送短信
     *
     * @param content 发送内容
     * @param mobile  发送的手机号码，多个手机号为用半角 , 分开
     * @param dstime  定时时间 ，为空时表示立即发送，格式：yyyy-MM-dd HH:mm:ss
     * @param msgid   客户自定义消息ID
     * @param ext     用户自定义扩展
     * @param msgfmt  提交消息编码格式（UTF-8/GBK）置空时默认是UTF-8 返 回 值：若用户自定义消息ID，则返回用户的ID，否则系统随机生成一个任务ID
     * @throws UnsupportedEncodingException
     */
    public String mt(String content, String mobile, String dstime, String msgid, String ext, String msgfmt) throws UnsupportedEncodingException {
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder params = new StringBuilder();
        params.append("username=").append(userName).append("&password=").append(this.getPwd()).append("&mobile=").append(mobile).append("&content=").append(content).append("&dstime=").append(dstime)
                .append("&ext=").append(ext).append("&msgid=").append(msgid).append("&msgfmt=").append(msgfmt);
        try {
            URL realUrl = new URL(HttpURL + "smsSend.do");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
            out.write(params.toString());
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 方法名称：mtData 功 能：发送个性短信(一个号码对应一条内容) 参数：
     *
     * @param content 发送内容，同号码个数一致，内容单条编码之后用英文逗号（,）隔开变成串，之后再对整个串进行二次编码，编码方式为UTF-8
     * @param mobile  发送的号码，多个号码用英文,分隔
     * @param dstime  定时时间
     * @param ext     用户自定义扩展
     * @param msgid   用户自定义消息ID
     * @param msgfmt  提交消息编码格式
     * @return
     */
    public String mtData(String content, String mobile, String dstime, String ext, String msgid, String msgfmt) {
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder params = new StringBuilder();
        params.append("username=").append(userName).append("&password=").append(this.getPwd()).append("&mobile=").append(mobile).append("&content=").append(content).append("&dstime=").append(dstime)
                .append("&ext=").append(ext).append("&msgid=").append(msgid).append("&msgfmt=").append(msgfmt);
        try {
            URL realUrl = new URL(HttpURL + "sendData.do");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
            out.write(params.toString());
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 方法名称：UpdatePassword 功 能：修改密码 参 数：newPassword(新密码) 返 回 值：状态报告（string）
     */
    public String UpdatePassword(String newPassword) {
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder params = new StringBuilder();
        params.append("username=").append(userName).append("&password=").append(this.getPwd()).append("&newpassword=").append(newPassword);
        try {
            URL realUrl = new URL(HttpURL + "passwordUpdate.do");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
            out.write(params.toString());
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 方法名称: getDigest 功 能：数字签名（明文MD5加密） 参数：
     *
     * @param plaintext 明文 返回参数：MD5密文
     */
    public String getDigest(String plaintext) {
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder params = new StringBuilder();
        params.append("plaintext=").append(plaintext);
        try {
            URL realUrl = new URL(HttpURL + "md5Digest.do");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
            out.write(params.toString());
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getHttpURL() {
        return HttpURL;
    }

    public void setHttpURL(String httpURL) {
        HttpURL = httpURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
