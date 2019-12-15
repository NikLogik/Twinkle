package ru.nachos.core.utils;

import org.jetbrains.annotations.NotNull;
import ru.nachos.core.Id;
import ru.nachos.core.exceptions.AgentMapOperationExeption;
import ru.nachos.core.exceptions.AgentMapSequenceException;
import ru.nachos.core.exceptions.FireLeaderException;
import ru.nachos.core.fire.FireUtils;
import ru.nachos.core.fire.lib.Agent;

import java.util.*;

public class AgentMap implements Iterable<Agent>{

//    Logger logger = Logger.getLogger(AgentMap.class);

    private Map<Id<Agent>, Agent> agentMap;
    private Id<Agent> headAgent;

    public AgentMap(){ agentMap = new HashMap<>(); }

    public AgentMap(Map<Id<Agent>, Agent> agentList){
        Id<Agent> head = FireUtils.getHeadAgent(agentList).getId();
        this.headAgent = head;
        this.agentMap = agentList;
    }

    public AgentMap(AgentMap map){
        this(map.getMap());
    }

    public Agent put(Id<Agent> key, Agent value){
        if (!agentMap.isEmpty()){
            if (value.isHead()){
                Id<Agent> id = FireUtils.getHeadAgent(agentMap).getId();
                if (id != null){
                    if (key.equals(id)){
                        return value;
                    } else {
                        value.setHead(false);
                    }
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
            this.headAgent = key;
        }
        return agentMap.put(key, value);
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
            put(next.getKey(), next.getValue());
            if (!agentMap.containsKey(next.getValue().getId())){
                counter++;
            }
        }
        return agentMap.size() == current + counter;
    }

    public boolean merge(AgentMap map){
        Map<Id<Agent>, Agent> var = map.getMap();
        return merge(var);
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

    public boolean checkSequence(){
        int counter = 0;
        List<Id<Agent>> heads = new ArrayList<>();
        Iterator<Agent> iterator = this.iterator();
        while (iterator.hasNext()){
            Agent var = iterator.next();
            if (var.isHead()){
                heads.add(var.getId());
            }
            counter++;
        }
        if (counter != agentMap.size()){
            throw new AgentMapSequenceException(AgentMapSequenceException.Code.BAD_SEQUENCE);
        }
        if (heads.size() < 1){
            throw  new AgentMapSequenceException(AgentMapSequenceException.Code.BAD_HEAD, new FireLeaderException(FireLeaderException.Code.MISSING));
        }
        if (heads.size() > 1){
            System.out.println(heads.toString());
            throw new AgentMapSequenceException(AgentMapSequenceException.Code.BAD_HEAD, new FireLeaderException(FireLeaderException.Code.TOO_MANY));
        }
        return counter == agentMap.size() && heads.size() == 1;
    }

    @NotNull
    @Override
    public Iterator<Agent> iterator() {
        return new AgentIterator();
    }

    private class AgentIterator implements Iterator<Agent>{

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
            current = agentMap.get(current).getRightNeighbour().getId();
            count++;
            return var;
        }
    }
}
