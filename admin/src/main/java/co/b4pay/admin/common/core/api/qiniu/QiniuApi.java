/**
 * Hola.YK Inc. Copyright (c) 2012-2015 All Rights Reserved.
 */
package co.b4pay.admin.common.core.api.qiniu;

import co.b4pay.admin.common.core.config.MainConfig;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * 七牛API
 * <p>
 * 错误码：http://developer.qiniu.com/docs/v6/api/reference/codes.html
 * </p>
 *
 * @author YK
 * @version $Id: QiniuApi.java, v 0.1 2015年1月22日 下午11:50:06 YK Exp $
 */
public final class QiniuApi {
    private static final Logger logger = Logger.getLogger(QiniuApi.class);

    // private static Object mutex = new Object();

    private final Auth auth;
    private final BucketManager bucketManager;
    private final UploadManager uploadManager;

    private final String publicBucket;
    private final String privateBucket;
    private final String publicDomain;
    private final String privateDomain;

    private QiniuApi() {
        auth = Auth.create(MainConfig.getConfig("QINIU_ACCESS_KEY"), MainConfig.getConfig("QINIU_SECRET_KEY"));
        bucketManager = new BucketManager(auth);
        uploadManager = new UploadManager();
        publicBucket = MainConfig.getConfig("QINIU_PUB_BUCKET");
        privateBucket = MainConfig.getConfig("QINIU_PRI_BUCKET");
        publicDomain = MainConfig.getConfig("QINIU_PUB_BUCKET_DOMAIN");
        privateDomain = MainConfig.getConfig("QINIU_PRI_BUCKET_DOMAIN");
    }

    /**
     * 上传文件到公共空间
     *
     * @param data
     * @param key
     * @return
     */
    public String uploadToPublic(final byte[] data, final String key) {
        return upload(data, key, getPublicUptoken(), publicDomain);
    }

    /**
     * 上传文件到公共空间
     *
     * @param file
     * @param key
     * @return
     */
    public String uploadToPublic(final File file, final String key) {
        return upload(file, key, getPublicUptoken(), publicDomain);
    }

    /**
     * 上传文件到公共空间
     *
     * @param filePath
     * @param key
     * @return
     */
    public String uploadToPublic(final String filePath, final String key) {
        return upload(filePath, key, getPublicUptoken(), publicDomain);
    }

    /**
     * 上传文件到私有空间
     *
     * @param data
     * @param key
     * @return
     */
    public String uploadToPrivate(final byte[] data, final String key) {
        return upload(data, key, getPrivateUptoken(), privateDomain);
    }

    /**
     * 上传文件到私有空间
     *
     * @param file
     * @param key
     * @return
     */
    public String uploadToPrivate(final File file, final String key) {
        return upload(file, key, getPrivateUptoken(), privateDomain);
    }

    /**
     * 上传文件到私有空间
     *
     * @param filePath
     * @param key
     * @return
     */
    public String uploadToPrivate(final String filePath, final String key) {
        return upload(filePath, key, getPrivateUptoken(), privateDomain);
    }

    /**
     * 抓取资源到公共空间
     *
     * @param url 要求url可公网正常访问
     * @param key
     * @return
     */
    public String fetchToPublic(final String url, final String key) {
        return fetch(url, key, publicBucket, publicDomain);
    }

    /**
     * 抓取资源到私有空间
     *
     * @param url 要求url可公网正常访问
     * @param key
     * @return
     */
    public String fetchToPrivate(final String url, final String key) {
        return fetch(url, key, privateBucket, privateDomain);
    }

    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、 http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @return
     */
    public String getPrivateDownloadUrl(final String baseUrl) {
        return auth.privateDownloadUrl(baseUrl);
    }

    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、 http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @param expires 有效时长，单位秒。默认3600s
     * @return
     */
    public String getPrivateDownloadUrl(final String baseUrl, final long expires) {
        return auth.privateDownloadUrl(baseUrl, expires);
    }

    /**
     * 删除公共bucket七牛空间文件
     *
     * @param key
     * @throws QiniuException
     */
    public void deletePublic(final String key) {
        try {
            bucketManager.delete(publicBucket, key);
        } catch (final QiniuException e) {
            final Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            if (r.statusCode != 612) {// 指定资源不存在或已被删除。
                logger.error("删除公共bucket七牛空间文件出错。" + e.getMessage(), e);
            }
        }
    }

    /**
     * 删除私有bucket七牛空间文件
     *
     * @param key
     * @throws QiniuException
     */
    public void deletePrivate(final String key) {
        try {
            bucketManager.delete(privateBucket, key);
        } catch (final QiniuException e) {
            final Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            if (r.statusCode != 612) {// 指定资源不存在或已被删除。
                logger.error("删除私有bucket七牛空间文件出错。" + e.getMessage(), e);
            }
        }
    }

    /**
     * 获取Bucket域名
     *
     * @return
     */
    public String getPublicDomain() {
        return publicDomain;
    }

    /**
     * 获取私有bucket上传授权uptoken<br>
     * 简单上传，使用默认策略
     *
     * @return
     */
    public String getPrivateUptoken() {
        return auth.uploadToken(privateBucket);// 请确保该bucket已经存在
    }

    /**
     * 获取公共bucket上传授权uptoken<br>
     * 简单上传，使用默认策略
     *
     * @return
     */
    public String getPublicUptoken() {
        return auth.uploadToken(publicBucket);// 请确保该bucket已经存在
    }

    // 上传内存中数据
    private String upload(final File file, final String key, final String uptoken, String domain) {
        try {
            final Response res = uploadManager.put(file, key, uptoken);
            final QiniuRet ret = res.jsonToObject(QiniuRet.class);
            if (res.isOK()) {
                return domain + ret.key;
            }
            if (logger.isInfoEnabled()) {
                logger.info(res);
                logger.info(ret);
            }
        } catch (final QiniuException e) {
            final Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    // 上传内存中数据
    private String upload(final byte[] data, final String key, final String uptoken, String domain) {
        try {
            final Response res = uploadManager.put(data, key, uptoken);
            final QiniuRet ret = res.jsonToObject(QiniuRet.class);
            if (res.isOK()) {
                return domain + ret.key;
            }
            if (logger.isInfoEnabled()) {
                logger.info(res);
                logger.info(ret);
            }
        } catch (final QiniuException e) {
            final Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    // 上传内存中数据
    private String upload(final String filePath, final String key, final String uptoken, String domain) {
        try {
            final Response res = uploadManager.put(filePath, key, uptoken);
            final QiniuRet ret = res.jsonToObject(QiniuRet.class);
            if (res.isOK()) {
                return domain + ret.key;
            }
            if (logger.isInfoEnabled()) {
                logger.info(res);
                logger.info(ret);
            }
        } catch (final QiniuException e) {
            final Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            // 响应的文本信息
            try {
                logger.error(r.bodyString());
            } catch (final QiniuException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        return null;
    }

    // 上传内存中数据
    private String fetch(final String url, final String key, final String bucket, String domain) {
        try {
            DefaultPutRet ret = bucketManager.fetch(url, bucket, key);
            if (StringUtils.isNotBlank(ret.key)) {
                return domain + ret.key;
            }
            if (logger.isInfoEnabled()) {
                logger.info(ret);
            }
        } catch (final QiniuException e) {
            final Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            // 响应的文本信息
            try {
                logger.error(r.bodyString());
            } catch (final QiniuException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        return null;
    }
}
