package co.b4pay.admin.common.core.resource;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.core.config.MainConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author YK
 * @version $Id v 0.1 2016年12月05日 15:21 Exp $
 */
public class ConfigResource {
    private static final Logger logger = LoggerFactory.getLogger(ConfigResource.class);

    private static final String SHOW_REQUEST_LOG = "mobile.request.log";

    private static final String MANAGER_WECHAT_OPENIDS = "manager.wechat.openids";

    private static final String DEV_MOBILES = "dev.mobiles";

    /**
     * 是否显示请求日志
     */
    private boolean showRequestLog = MainConfig.isDevMode;

    /**
     * 接受审核通知的管理员列表
     */
    private String[] managerWechatOpenids;

    public ConfigResource() {

    }

    /**
     * 开发者手机号
     */
    private Set<String> devMobiles = Collections.emptySet();

    public void updateResource(String key, String value) {
        if (StringUtil.equals(key, SHOW_REQUEST_LOG)) {
            this.showRequestLog = Boolean.valueOf(value);
        } else if (StringUtil.equals(key, MANAGER_WECHAT_OPENIDS)) {
            this.managerWechatOpenids = StringUtil.split(value, ",");
        } else if (StringUtil.equals(key, DEV_MOBILES)) {
            this.devMobiles = new HashSet<>(Arrays.asList(StringUtil.split(value, ",")));
        } else {
            logger.warn("未知的资源更新请求，key=[{}]，value=[{}]", key, value);
        }
    }

    public boolean isShowRequestLog() {
        return showRequestLog;
    }

    public String[] getManagerWechatOpenids() {
        return managerWechatOpenids;
    }

    public Set<String> getDevMobiles() {
        return devMobiles;
    }
}
