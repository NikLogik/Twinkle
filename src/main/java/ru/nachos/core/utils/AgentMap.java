package ru.nachos.core.utils;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.nachos.core.Id;
import ru.nachos.core.exceptions.AgentMapOperationExeption;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.AgentStatus;

import java.util.*;

public class AgentMap implements Iterable<Agent>{

    Logger logger = Logger.getLogger(AgentMap.class);

    private Map<Id<Agent>, Agent> agentMap;
    private Set<Id<Agent>> disabled = new TreeSet<>();
    private Set<Id<Agent>> stopped = new TreeSet<>();
    private Id<Agent> headAgent;

    public AgentMap(){ agentMap = new HashMap<>(); }

    public AgentMap(Map<Id<Agent>, Agent> agentList){
        this.headAgent = FireUtils.getHeadAgent(agentList).getId();
        this.agentMap = new HashMap<>(agentList);
    }

    public AgentMap(AgentMap map){
        this(map.getMap());
    }

    public Agent put(Id<Agent> key, Agent value){
        if (!agentMap.isEmpty()){
            if (value.isHead()){
                if (key.toString().equals(headAgent.toString())){
                    return value;
                } else {
                    value.setHead(false);
                }
            }
            Agent rightNeighbour = value.getRightNeighbour();
            Agent leftNeighbour = value.getLeftNeighbour();
            if (rightNeighbour != null && leftNeighbour != null){
                if (!agentMap.containsKey(rightNeighbour.getId())){
                    agentMap.putIfAbsent(rightNeighbour.getId(), rightNeighbour);
                }
                agentMap.get(rightNeighbour.getId()).setLeftNeighbour(value);
                if (!agentMap.containsKey(leftNeighbour.getId())){
                    agentMap.putIfAbsent(leftNeighbour.getId(), leftNeighbour);
                }
                agentMap.get(leftNeighbour.getId()).setRightNeighbour(value);
            } else {
                throw new AgentMapOperationExeption("Can`t put new item with ID=" + key +  "without any neighbours mapping.");
            }
        } else if (agentMap.isEmpty()){
            value.setHead(true);
            this.headAgent = key;
        }
        return agentMap.put(key, value);
    }

    public Agent getHead(){
        return agentMap.get(headAgent);
    }

    public Agent get(Id<Agent> key){ return agentMap.get(key); }

    public void putAll(Map<Id<Agent>, Agent> map){
        for (Agent agent : map.values()){
            put(agent.getId(), agent);
        }
    }

    public boolean merge(Map<Id<Agent>, Agent> map){
        int current = agentMap.size();
        int counter = 0;
        Iterator<Map.Entry<Id<Agent>, Agent>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Id<Agent>, Agent> next = iterator.next();
            if (!agentMap.containsKey(next.getValue().getId())){
                counter++;
            }
            put(next.getKey(), next.getValue());
        }
        return agentMap.size() == current + counter;
    }

    public boolean merge(AgentMap map){
        Map<Id<Agent>, Agent> var = map.getMap();
        return merge(var);
    }

    public Id<Agent> setToStopped(Id<Agent> agentId){
        Agent agent = agentMap.get(agentId);
        agent.setStatus(AgentStatus.STOPPED);
        stopped.add(agentId);
        logger.warn("Agent with ID=" + agentId.toString() + " change status to STOPPED");
        return agentId;
    }

    public Id<Agent> setToDisable(Id<Agent> agentId){
        Agent agent = agentMap.get(agentId);
        agent.setStatus(AgentStatus.DISABLED);
        if (agent.isHead()){
            agent.getRightNeighbour().setHead(true);
            headAgent = agent.getRightNeighbour().getId();
        }
        agent.getRightNeighbour().setLeftNeighbour(agent.getLeftNeighbour());
        agent.getLeftNeighbour().setRightNeighbour(agent.getRightNeighbour());
        agent.setRightNeighbour(null);
        agent.setLeftNeighbour(null);
        if (stopped.removeIf(agentId1 -> agentId1.equals(agentId))){
            logger.warn("Agent with ID=" + agentId.toString() + " change status from STOPPED to DISABLED");
        }
        disabled.add(agentId);
        logger.warn("Agent with ID=" + agentId.toString() + " change status to DISABLED");
        return agentId;
    }

    public Agent remove(Id<Agent> key){
        if (agentMap.containsKey(key)){
            Agent agent = agentMap.get(key);
            if (agent.isHead()){
                agent.getRightNeighbour().setHead(true);
                headAgent = agent.getRightNeighbour().getId();
            }
            agent.getRightNeighbour().setLeftNeighbour(agent.getLeftNeighbour());
            agent.getLeftNeighbour().setRightNeighbour(agent.getRightNeighbour());
        }
        return agentMap.remove(key);
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

    public boolean containsKey(Id<Agent> key){
        return agentMap.containsKey(key);
    }

    public int size(){
        return agentMap.keySet().size();
    }

    public boolean isEmpty(){
        return agentMap.size() == 0;
    }

    public void clear(){
        agentMap.clear();
    }

    @NotNull
    @Override
    public Iterator<Agent> iterator() {
        return new AgentIterator();
    }

    public Iterator<Agent> reverseIterator() { return new AgentReverseIterator(); }

    private class AgentIterator implements Iterator<Agent>{

        int size = agentMap.size() - disabled.size();
        int count = 0;
        Id<Agent> current = headAgent;

        @Override
        public boolean hasNext() { return count < size; }

        @Override
        public Agent next() {
            Agent var = agentMap.get(current);
            current = agentMap.get(current).getRightNeighbour().getId();
            count++;
            return var;
        }

        @Override
        public void remove() {
            Agent var = agentMap.get(current);
            var.getLeftNeighbour().setRightNeighbour(var.getRightNeighbour());
            var.getRightNeighbour().setLeftNeighbour(var.getLeftNeighbour());
            current = var.getRightNeighbour().getId();
            var.setLeftNeighbour(null);
            var.setRightNeighbour(null);
        }
    }

    private class AgentReverseIterator implements Iterator<Agent>{
        int size = agentMap.size();
        int count = 0;
        Id<Agent> current = headAgent;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Agent next() {
            Agent var = agentMap.get(current);
            current = agentMap.get(current).getLeftNeighbour().getId();
            count++;
            return var;
        }
    }
}
