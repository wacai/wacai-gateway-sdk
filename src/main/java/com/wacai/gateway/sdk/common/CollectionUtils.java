package com.wacai.gateway.sdk.common;

import java.util.Map;
import java.util.TreeMap;

/**
 * map 工具类
 *
 * @author bairen
 */
public final class CollectionUtils {

    /**
     * 返回Key大小写不区分的Map
     *
     * @return
     */
    public static Map<String, String> newKeyCaseInsensitiveMap() {
        return new KeyCaseInsensitiveMap<String>();
    }

    private static class KeyCaseInsensitiveMap<V> extends TreeMap<String, V> {

        private static final long serialVersionUID = 1L;

        private KeyCaseInsensitiveMap() {
            super(String.CASE_INSENSITIVE_ORDER);
        }

    }
}
