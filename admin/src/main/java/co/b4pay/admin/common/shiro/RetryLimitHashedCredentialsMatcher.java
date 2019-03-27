package co.b4pay.admin.common.shiro;

import co.b4pay.admin.common.core.cache.CacheManager;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 重试次数限定匹配验证器
 *
 * @author YK
 * @version $Id: RetryLimitHashedCredentialsMatcher.java, v 0.1 2016年5月16日 下午3:13:06 YK Exp $
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    // private static final Logger logger = LoggerFactory.getLogger(SystemAuthenticationFilter.class);
    //private static final LRUCache<String, AtomicInteger> cache = new LRUCache<String, AtomicInteger>(10);

    private static final String CACHE_PREFIX = "LOGIN_RETRY_";

    @Autowired
    private org.springframework.cache.CacheManager cacheManager;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        // retry count + 1
        AtomicInteger retryCount = getFromCache(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            setInCache(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 3) {
            // if retry count > 3 throw
            throw new ExcessiveAttemptsException("错误重试达到3次，账户锁定一小时");
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // clear retry count
            remove(username);
        }

        return matches;
    }

    private AtomicInteger getFromCache(String username) {
        return getCache().get(CACHE_PREFIX + username, AtomicInteger.class);
    }

    private void setInCache(String username, AtomicInteger retryCount) {
        getCache().put(CACHE_PREFIX + username, retryCount);
    }

    private void remove(String username) {
        getCache().evict(CACHE_PREFIX + username);
    }

    private Cache getCache() {
        // 缓存一小时
        return cacheManager.getCache(CacheManager.ONEHOUR);
    }
}
