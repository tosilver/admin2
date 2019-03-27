package co.b4pay.admin.common.core.cache;

/**
 * 缓存管理器
 *
 * @author YK
 * @version $Id: CacheManager.java, v 0.1 2015-5-11 下午7:37:53 YK Exp $
 */
public class CacheManager {

    /**
     * fiveMinutes
     */
    public static final String FIVE_MINUTES = "fiveMinutes";
    /**
     * tenMinutes
     */
    public static final String TEN_MINUTES = "tenMinutes";
    /**
     * session
     */
    public static final String SESSION = "session";
    /**
     * halfHour
     */
    public static final String HALFHOUR = "halfHour";
    /**
     * oneHour
     */
    public static final String ONEHOUR = "oneHour";
    /**
     * oneDay
     */
    public static final String ONEDAY = "oneDay";

    /**
     * 从fiveMinutes缓存中取出对象
     *
     * @param key
     * @return
     */
    public static <T> T getFromFiveMinutes(String key) {
        return CacheKit.get(FIVE_MINUTES, key);
    }

    /**
     * 保存到fiveMinutes缓存中取出对象
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInFiveMinutes(String key, Object value) {
        CacheKit.put(FIVE_MINUTES, key, value);
    }

    /**
     * 删除oneHour缓存中的对象
     *
     * @param key
     * @return
     */
    public static void removeFiveMinutes(String key) {
        CacheKit.remove(FIVE_MINUTES, key);
    }

    /**
     * 从tenMinutes缓存中取出对象
     *
     * @param key
     * @return
     */
    public static <T> T getFromTenMinutes(String key) {
        return CacheKit.get(TEN_MINUTES, key);
    }

    /**
     * 保存到tenMinutes缓存中取出对象
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInTenMinutes(String key, Object value) {
        CacheKit.put(TEN_MINUTES, key, value);
    }

    /**
     * 删除oneHour缓存中的对象
     *
     * @param key
     * @return
     */
    public static void removeTenMinutes(String key) {
        CacheKit.remove(TEN_MINUTES, key);
    }

    /**
     * 从session缓存中取出对象
     *
     * @param key
     * @return
     */
    public static <T> T getFromSession(String key) {
        return CacheKit.get(SESSION, key);
    }

    /**
     * 保存到session缓存中取出对象
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInSession(String key, Object value) {
        CacheKit.put(SESSION, key, value);
    }

    /**
     * 删除session缓存中的对象
     *
     * @param key
     * @return
     */
    public static void removeSession(String key) {
        CacheKit.remove(SESSION, key);
    }

    /**
     * 从halfHour缓存中取出对象
     *
     * @param key
     * @return
     */
    public static <T> T getFromHalfHour(String key) {
        return CacheKit.get(HALFHOUR, key);
    }

    /**
     * 保存到halfHour缓存中取出对象
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInHalfHour(String key, Object value) {
        CacheKit.put(HALFHOUR, key, value);
    }

    /**
     * 删除halfHour缓存中的对象
     *
     * @param key
     * @return
     */
    public void removeHalfHour(String key) {
        CacheKit.remove(HALFHOUR, key);
    }

    /**
     * 从oneHour缓存中取出对象
     *
     * @param key
     * @return
     */
    public static <T> T getFromOneHour(String key) {
        return CacheKit.get(ONEHOUR, key);
    }

    /**
     * 保存到oneHour缓存中取出对象
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInOneHour(String key, Object value) {
        CacheKit.put(ONEHOUR, key, value);
    }

    /**
     * 删除oneHour缓存中的对象
     *
     * @param key
     * @return
     */
    public static void removeOneHour(String key) {
        CacheKit.remove(ONEHOUR, key);
    }

    /**
     * 从oneDay缓存中取出对象
     *
     * @param key
     * @return
     */
    public static <T> T getFromOneDay(String key) {
        return CacheKit.get(ONEDAY, key);
    }

    /**
     * 保存到oneDay缓存中取出对象
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInOneDay(String key, Object value) {
        CacheKit.put(ONEDAY, key, value);
    }

    /**
     * 删除oneDay缓存中的对象
     *
     * @param key
     * @return
     */
    public static void removeOneDay(String key) {
        CacheKit.remove(ONEDAY, key);
    }
}