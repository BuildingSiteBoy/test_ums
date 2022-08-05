package com.zane.test_ums.config.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.zane.test_ums.util.JwtUtil;
import com.zane.test_ums.util.RedisUtil;
import com.zane.test_ums.util.SpringContextUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * 重写Shiro框架内的Cache读写
 * @author Zanezeng
 */
//@Component
public class MyCache<K, V> implements Cache<K, V> {

    RedisUtil redisUtils = SpringContextUtil.getContext().getBean(RedisUtil.class);

//    public MyCache() {
//        if (null == redisUtils) {
//            redisUtils = SpringContextUtil.getContext().getBean(RedisUtil.class);
//        }
//    }

    private static final String PREFIX_REDIS_USER = "zane_user:";
    private static final String PREFIX_REDIS_TOKEN = "zane_token:";
    private static final long REDIS_EXPIRE_TIME = 3000;

    /**
     * 获取key
     * @param key key
     * @return zane_user:{token}
     */
    private String getKey(Object key) {
        return PREFIX_REDIS_USER + JwtUtil.decode(key.toString());
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if (!redisUtils.hasKey(this.getKey(key))) {
            return null;
        }
        return redisUtils.get(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置redis的shiro缓存：加上缓存时间
        return redisUtils.set(this.getKey(key), value, REDIS_EXPIRE_TIME);
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if (!redisUtils.hasKey(this.getKey(key))) {
            return null;
        }
        redisUtils.del(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        redisUtils.clear();
    }

    /**
     * 获取缓存的个数
     */
    @Override
    public int size() {
        return this.keys().size();
    }

    /**
     * 获取所有key
     */
    @Override
    public Set keys() {
        return redisUtils.getKeys();
    }

    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<>();
        for (Object key : keys) {
            values.add(redisUtils.get(this.getKey(key)));
        }
        return values;
    }
}
