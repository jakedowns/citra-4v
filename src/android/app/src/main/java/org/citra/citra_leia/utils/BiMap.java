package org.citra.citra_leia.utils;

import java.util.HashMap;
import java.util.Map;

public class BiMap<K, V> {
    private Map<K, V> forward = new HashMap<K, V>();
    private Map<V, K> backward = new HashMap<V, K>();

    public synchronized void add(K key, V value) {
        forward.put(key, value);
        backward.put(value, key);
    }

    public synchronized V getForward(K key) {
        return forward.get(key);
    }

    public synchronized K getBackward(V key) {
        return backward.get(key);
    }
}
