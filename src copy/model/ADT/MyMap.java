package model.ADT;

import exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyMap <K, V> implements MyIMap<K, V> {
    private Map<K, V> map;
    public MyMap() {
        this.map = new HashMap<K, V>();
    }
    public MyMap(Map<K, V> map) {
        this.map = map;
    }
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key not found");
        }
        return this.map.get(key);
    }
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }
    public void put(K key, V value) {
        this.map.put(key, value);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (Map.Entry<K, V> entry : this.map.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }

    public void remove(K key) {
        this.map.remove(key);
    }

    public MyIMap<K,V> deepCopy() {
        return new MyMap(this.map);
    }
}
