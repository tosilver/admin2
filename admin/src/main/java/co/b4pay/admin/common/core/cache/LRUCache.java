package co.b4pay.admin.common.core.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3801124978530219131L;

    // 定义缓存的容量
    private final int maxSize;

    public LRUCache(int maxSize) {
        this(maxSize, 16, 0.75F, true);
    }

    public LRUCache(int maxSize, int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this.maxSize = maxSize;
    }

    public V get(K key, IDataLoader<V> dataLoader) {
        V data = get(key);
        if (data == null) {
            data = dataLoader.load();
            put(key, data);
        }
        return data;
    }

    public interface IDataLoader<V> {
        public V load();
    }

    // 实现LRU的关键方法，如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.maxSize;
    }

}