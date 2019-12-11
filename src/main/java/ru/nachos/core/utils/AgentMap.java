package ru.nachos.core.utils;

import org.jetbrains.annotations.NotNull;
import ru.nachos.core.Id;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.lib.Agent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AgentMap implements Iterable<Agent>{

    private Map<Id<Agent>, Agent> agentMap;

    public AgentMap(Map<Id<Agent>, Agent> agentList){
        this.agentMap = agentList;
    }

    public Agent put(Id<Agent> key, Agent value){
        return agentMap.put(key, value);
    }

    public void putAll(Map<Id<Agent>, Agent> map){
        agentMap.putAll(map);
    }

    public Agent remove(Id<Agent> key){
        return agentMap.remove(key);
    }

    public boolean remove(Id<Agent> key, Agent value){
        return agentMap.remove(key, value);
    }

    public Map<Id<Agent>, Agent> getMap() {
        return this.agentMap;
    }

    public Collection<Agent> values(){
        return agentMap.values();
    }

    public Set<Id<Agent>> keySet(){
        return agentMap.keySet();
    }

    public void clear(){
        agentMap.clear();
    }

    @NotNull
    @Override
    public Iterator<Agent> iterator() {
        return new AgentIterator();
    }

    private class AgentIterator implements Iterator<Agent>{

        int size = agentMap.size();
        int count = 0;
        Id<Agent> current = FireUtils.getHeadAgent(agentMap).getId();

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Agent next() {
            Agent var = agentMap.get(current);
            current = agentMap.get(current).getRightNeighbour().getId();
            count++;
            return var;
        }
    }
}
