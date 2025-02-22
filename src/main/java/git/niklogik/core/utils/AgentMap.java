package git.niklogik.core.utils;

import git.niklogik.core.exceptions.AgentMapOperationExeption;
import git.niklogik.core.fire.FireUtils;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.AgentStatus;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AgentMap implements Iterable<Agent> {

    Logger logger = LoggerFactory.getLogger(AgentMap.class);

    private Map<UUID, Agent> agentMap;
    private Set<UUID> disabled = new TreeSet<>();
    private Set<UUID> stopped = new TreeSet<>();
    private UUID headAgent;

    public AgentMap() {agentMap = new HashMap<UUID, Agent>();}

    public AgentMap(Map<UUID, Agent> agentList) {
        this.headAgent = FireUtils.getHeadAgent(agentList).getId();
        this.agentMap = new HashMap<>(agentList);
    }

    public Agent put(UUID key, Agent value) {
        if (agentMap.isEmpty()) {
            value.setHead(true);
            this.headAgent = key;
            return agentMap.put(key, value);
        }

        if (value.isHead()) {
            if (key.toString().equals(headAgent.toString())) {
                return value;
            } else {
                value.setHead(false);
            }
        }
        var rightNeighbour = value.getRightNeighbour();
        var leftNeighbour = value.getLeftNeighbour();
        if (rightNeighbour != null && leftNeighbour != null) {
            if (!agentMap.containsKey(rightNeighbour.getId())) {
                agentMap.putIfAbsent(rightNeighbour.getId(), rightNeighbour);
            }
            agentMap.get(rightNeighbour.getId()).setLeftNeighbour(value);
            if (!agentMap.containsKey(leftNeighbour.getId())) {
                agentMap.putIfAbsent(leftNeighbour.getId(), leftNeighbour);
            }
            agentMap.get(leftNeighbour.getId()).setRightNeighbour(value);
        } else {
            throw new AgentMapOperationExeption(
                "Can`t put new item with ID=" + key + "without any neighbours mapping.");
        }
        return agentMap.put(key, value);
    }

    public Agent getHead() {
        return agentMap.get(headAgent);
    }

    public Agent get(UUID key) {return agentMap.get(key);}

    public void putAll(Map<UUID, Agent> map) {
        for (Agent agent : map.values()) {
            put(agent.getId(), agent);
        }
    }

    public boolean merge(Map<UUID, Agent> map) {
        int current = agentMap.size();
        int counter = 0;
        for (Map.Entry<UUID, Agent> next : map.entrySet()) {
            if (!agentMap.containsKey(next.getValue().getId())) {
                counter++;
            }
            put(next.getKey(), next.getValue());
        }
        return agentMap.size() == current + counter;
    }

    public void merge(AgentMap map) {
        Map<UUID, Agent> var = map.getMap();
        merge(var);
    }

    public void stopAgent(UUID agentId) {
        Agent agent = agentMap.get(agentId);
        agent.setStatus(AgentStatus.STOPPED);
        stopped.add(agentId);
        logger.warn("Agent with ID={} change status to STOPPED", agentId);
    }

    public void disableAgent(UUID agentId) {
        Agent agent = agentMap.get(agentId);
        agent.setStatus(AgentStatus.DISABLED);
        if (agent.isHead()) {
            agent.getRightNeighbour().setHead(true);
            headAgent = agent.getRightNeighbour().getId();
        }
        agent.getRightNeighbour().setLeftNeighbour(agent.getLeftNeighbour());
        agent.getLeftNeighbour().setRightNeighbour(agent.getRightNeighbour());
        agent.setRightNeighbour(null);
        agent.setLeftNeighbour(null);
        if (stopped.removeIf(agentId1 -> agentId1.equals(agentId))) {
            logger.warn("Agent with ID= {} change status from STOPPED to DISABLED", agentId);
        }
        disabled.add(agentId);
        logger.warn("Agent with ID={} change status to DISABLED", agentId);

    }

    public Agent remove(UUID key) {
        if (agentMap.containsKey(key)) {
            Agent agent = agentMap.get(key);
            if (agent.isHead()) {
                agent.getRightNeighbour().setHead(true);
                headAgent = agent.getRightNeighbour().getId();
            }
            agent.getRightNeighbour().setLeftNeighbour(agent.getLeftNeighbour());
            agent.getLeftNeighbour().setRightNeighbour(agent.getRightNeighbour());
        }
        return agentMap.remove(key);
    }

    public Map<UUID, Agent> getMap() {
        return this.agentMap;
    }

    public Collection<Agent> values() {
        return agentMap.values();
    }

    public Set<UUID> keySet() {
        return agentMap.keySet();
    }

    public boolean containsKey(UUID key) {
        return agentMap.containsKey(key);
    }

    public int size() {
        return agentMap.size();
    }

    public boolean isEmpty() {
        return agentMap.isEmpty();
    }

    public void clear() {
        agentMap.clear();
    }

    @Override
    public @NotNull Iterator<Agent> iterator() {
        return new AgentIterator();
    }

    public Iterator<Agent> reverseIterator() {return new AgentReverseIterator();}

    private class AgentIterator implements Iterator<Agent> {

        int size = agentMap.size() - disabled.size();
        int count = 0;
        UUID current = headAgent;

        @Override
        public boolean hasNext() {return count < size;}

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

    private class AgentReverseIterator implements Iterator<Agent> {
        int size = agentMap.size();
        int count = 0;
        UUID current = headAgent;

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
