package ru.nachos.core;

import ru.nachos.core.fire.lib.Agent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Id<T> implements Comparable<Id<T>> {

    private static final Map<Class<?>, Map<String, Id<?>>> cache = new ConcurrentHashMap();

    public Id() {
    }

    public static <T>Id<T> create(long key, Class<T> type) { return create(Long.toString(key), type); }

    public static <T> Id<T> create(Id<?> id, Class<T> type) {
        return id == null ? null : create(id.toString(), type);
    }

    public static <T> Id<T> create(String key, Class<T> type) {
        Map<String, Id<?>> map = (Map)cache.get(type);
        if (map == null) {
            map = new ConcurrentHashMap();
            cache.put(type, map);
        }

        Id<?> id = (Id)((Map)map).get(key);
        if (id == null) {
            id = new Id.IdImpl(key);
            ((Map)map).put(key, id);
        }

        return (Id)id;
    }

    public int compareTo(Id<T> o) throws IllegalArgumentException {
        int res = this.toString().compareTo(o.toString());
        return res;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Id) {
            return this.compareTo((Id)obj) == 0;
        } else {
            return false;
        }
    }

    private static class IdImpl<T> extends Id<T> {
        private final String id;

        IdImpl(String id) {
            this.id = id;
        }

        public int hashCode() {
            return this.id.hashCode();
        }

        public String toString() {
            return this.id;
        }
    }
}
