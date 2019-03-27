/**
 * http://www.wdb168.com/
 * Copyright (c) 微贷宝. 2014-2015 All Rights Reserved.
 */
package co.b4pay.admin.common.core.cache;

/**
 * IDataLoader.
 * <p>
 * Example:
 * <p>
 * <pre>
 * List&lt;Blog&gt; blogList = EhCacheKit.handle(&quot;blog&quot;, &quot;blogList&quot;, new IDataLoader() {
 *     public Object load() {
 *         return Blog.dao.find(&quot;select * from blog&quot;);
 *     }
 * });
 * </pre>
 *
 * @author YK
 * @version $Id: IDataLoader.java, v 0.1 2015年4月23日 上午10:37:06 YK Exp $
 */
public interface IDataLoader {
    Object load();
}
