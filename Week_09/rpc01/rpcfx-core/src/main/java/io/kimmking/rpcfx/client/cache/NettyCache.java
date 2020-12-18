package io.kimmking.rpcfx.client.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ty
 *
 * 请求结果缓存
 *
 * 简单功能，还得加上缓存时长等功能
 */
public class NettyCache {

    private static final Map<String, String> CACHE = new ConcurrentHashMap<>(16);

    public static void put(final String key, final String val) {
        CACHE.put(key, val);
    }

    public static String get(final String key) {
        return CACHE.getOrDefault(key, "");
    }

    public static int size(){
        return CACHE.size();
    }
}
