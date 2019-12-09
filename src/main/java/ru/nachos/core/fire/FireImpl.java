package ru.nachos.core.fire;

import com.vividsolutions.jts.geom.Coordinate;
import ru.nachos.core.Id;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Agent;
import ru.nachos.core.fire.lib.Fire;
import ru.nachos.core.fire.lib.FireFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class describes firefront
 */
class FireImpl implements Fire {
    /**
     * Name for idetification fire instance
     */
    private String name;

    /**
     * Center point, where fire is started to spread.
     * Use rectangular coordinates WGS84
     */
    private Coordinate center;

    /**
     * Length of firefront at the beginning of simulation
     */
    private int perimeter;
    /**
     * Units for calculation max distance between neighbours agents
     * in the firefront along simulation
     */
    private int agentDistance;
    /**
     * The speed of fire spread, without relief constraint
     */
    private double fireSpeed;
    /**
     * The class of fire
     */
    private int fireClass;
    /**
     * Agents, which was created along simulation
     */
    private Map<Id<Agent>, Agent> twinkles = new LinkedHashMap<>();
    private FireFactory factory = new FireFactoryImpl();
    FireImpl(){}

    FireImpl(Config config){
        this.name = config.getFireName();
        this.perimeter = config.getFirePerimeter();
        this.agentDistance = config.getFireAgentsDistance();
        this.center = config.getFireCenterCoordinate();
        this.fireClass = config.getFireClass().getValue();
    }

    @Override
    public FireFactory getFactory(){ return this.factory; }

    @Override
    public String getName() { return name; }
    @Override
    public Coordinate getCenterPoint() { return center; }
    @Override
    public int getPerimeter() { return perimeter; }
    @Override
    public int getFireClass() { return fireClass; }
    @Override
    public int getAgentDistance() { return agentDistance; }
    @Override
    public double getFireSpeed() { return fireSpeed; }
    @Override
    public Map<Id<Agent>, Agent> getTwinkles() { return twinkles; }
    @Override
    public Agent addAgent(Agent agent){
        if (twinkles.containsKey(agent.getId())){
            throw new IllegalArgumentException("Twinkle with id: " + agent.getId() + " has already existed");
        } else {
            if (!(agent instanceof Twinkle)) {
                throw new IllegalArgumentException("Bad attemption adding twinkle with id " + agent.getId() + ". It`s not the instance of Twinkle class");
            }
            return twinkles.put(agent.getId(), agent);
        }
    }
    @Override
    public boolean removeAgent(Agent agent){ return twinkles.remove(agent.getId(), agent); }
    @Override
    public Agent removeAgent(Id<Agent> id){ return twinkles.remove(id); }
    void setName(String name){ this.name = name; }
    @Override
    public void setCenterPoint(Coordinate center){
        this.center = center;
    }

    void setPerimeter(int perimeter){ this.perimeter = perimeter; }

    void setAgentDistance(int agentDistance){ this.agentDistance = agentDistance; }

    @Override
    public void setFireSpeed(double fireSpeed){ this.fireSpeed = fireSpeed; }

    public void setFireClass(int fireClass) { this.fireClass = fireClass; }
}
