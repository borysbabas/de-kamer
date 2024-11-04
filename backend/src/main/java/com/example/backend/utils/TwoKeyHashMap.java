package com.example.backend.utils;

import java.util.HashMap;
import java.util.Map;

public class TwoKeyHashMap<K1, K2, V> {
    private final Map<K1, Map<K2, V>> map;
    public TwoKeyHashMap() {
        map = new HashMap<>();
    }

    public V put(K1 k1, K2 k2, V v) {
        Map<K2, V> k2Map = null;
        if (map.containsKey(k1)) {
            k2Map = map.get(k1);
        } else {
            k2Map = new HashMap<>();
            map.put(k1, k2Map);
        }
        return k2Map.put(k2, v);
    }

    public V get(K1 k1, K2 k2) {
        if (map.containsKey(k1)) {
            return map.get(k1).get(k2);
        }
        return null;
    }

    public boolean containsKeys(K1 k1, K2 k2) {
        V v = get(k1, k2);
        return v != null;
    }
}
