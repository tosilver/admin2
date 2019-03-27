/**
 * http://www.wdb168.com/
 * Copyright (c) 微贷宝. 2014-2015 All Rights Reserved.
 */
package co.b4pay.admin.common.core.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Plugins.
 *
 * @author YK
 * @version $Id: Plugins.java, v 0.1 2015年4月23日 上午10:54:05 YK Exp $
 */
public final class Plugins {

    private List<IPlugin> pluginList = new ArrayList<IPlugin>();

    public Plugins add(IPlugin plugin) {
        if (plugin != null)
            this.pluginList.add(plugin);
        return this;
    }

    /**
     * Getter method for property <tt>pluginList</tt>.
     *
     * @return property value of pluginList
     */
    public List<IPlugin> getPluginList() {
        return pluginList;
    }

    /**
     * Setter method for property <tt>pluginList</tt>.
     *
     * @param pluginList value to be assigned to property pluginList
     */
    public void setPluginList(List<IPlugin> pluginList) {
        this.pluginList = pluginList;
    }

    public int size() {
        return pluginList.size();
    }

}
