package ru.nachos.core;

import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.network.lib.Link;
import ru.nachos.core.network.lib.Node;
import ru.nachos.core.network.lib.PolygonV2;

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

    public static Id<Agent> createAgentId(long key){return create(key, Agent.class);}
    public static Id<Agent> createAgentId(String key){return create(key, Agent.class);}
    public static Id<Agent> createAgentId(Id<Agent> key){return create(key, Agent.class);}
    public static Id<Link> createLinkId(long key){return create(key, Link.class);}
    public static Id<Link> createLinkId(String key){return create(key, Link.class);}
    public static Id<Link> createLinkId(Id<Link> key){return create(key, Link.class);}
    public static Id<Node> createNodeId(long key) {return create(key, Node.class);}
    public static Id<Node> createNodeId(String key) {return create(key, Node.class);}
    public static Id<Node> createNodeId(Id<Node> key) {return create(key, Node.class);}
    public static Id<PolygonV2> createPolygonId(Id<PolygonV2> key) {return create(key, PolygonV2.class);}
    public static Id<PolygonV2> createPolygonId(long key) {return create(key, PolygonV2.class);}
    public static Id<PolygonV2> createPolygonId(String key) {return create(key, PolygonV2.class);}

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
