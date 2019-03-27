/**
 * http://www.wdb168.com/
 * Copyright (c) 微贷宝. 2014-2015 All Rights Reserved.
 */
package co.b4pay.admin.common.core.cache;

import co.b4pay.admin.common.core.plugin.IPlugin;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

import java.io.InputStream;
import java.net.URL;

/**
 * EhCachePlugin.
 *
 * @author YK
 * @version $Id: EhCachePlugin.java, v 0.1 2015年4月23日 上午10:42:17 YK Exp $
 */
public class EhCachePlugin implements IPlugin {

    private static CacheManager cacheManager;
    private String configurationFileName;
    private URL configurationFileURL;
    private InputStream inputStream;
    private Configuration configuration;

    public EhCachePlugin() {

    }

    public EhCachePlugin(final String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    public EhCachePlugin(final URL configurationFileURL) {
        this.configurationFileURL = configurationFileURL;
    }

    public EhCachePlugin(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public EhCachePlugin(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean start() {
        createCacheManager();
        CacheKit.init(cacheManager);
        return true;
    }

    private void createCacheManager() {
        if (cacheManager != null) {
            return;
        }

        if (configurationFileName != null) {
            cacheManager = CacheManager.create(configurationFileName);
            return;
        }

        if (configurationFileURL != null) {
            cacheManager = CacheManager.create(configurationFileURL);
            return;
        }

        if (inputStream != null) {
            cacheManager = CacheManager.create(inputStream);
            return;
        }

        if (configuration != null) {
            cacheManager = CacheManager.create(configuration);
            return;
        }

        cacheManager = CacheManager.create();
    }

    @Override
    public boolean stop() {
        cacheManager.shutdown();
        return true;
    }
}
