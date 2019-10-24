package ru.nachos.core.fire;

import ru.nachos.core.Coord;
import ru.nachos.core.Id;
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
    private Coord center;
    /**
     * Length of firefront at the beginning of simulation
     */
    private int perimeter;
    /**
     * Units for calculation max distance between neighbours agents
     * in the firefront along simulation
     */
    private int accuracy;
    /**
     * The speed of fire spread, without relief constraint
     */
    private double fireSpeed;
    /**
     * Agents, which was created along simulation
     */
    private Map<Id<Agent>, Agent> twinkles = new LinkedHashMap<>();
    private FireFactory factory;

    FireImpl(FireFactory factory){
        this.factory = factory;
    }

    public FireFactory getFactory(){ return this.factory; }

    public String getName() { return name; }

    public Coord getCenter() { return center; }

    public int getPerimeter() { return perimeter; }

    public int getAccuracy() { return accuracy; }

    public double getFireSpeed() { return fireSpeed; }

    public Map<Id<Agent>, Agent> getTwinkles() { return twinkles; }

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

    public boolean removeAgent(Agent agent){ return twinkles.remove(agent.getId(), agent); }

    public Agent removeAgent(Id<Agent> id){ return twinkles.remove(id); }

    void setName(String name){ this.name = name; }

    void setCenter(Coord center){ this.center = center; }

    void setPerimeter(int perimeter){ this.perimeter = perimeter; }

    void setAccuracy(int accuracy){ this.accuracy = accuracy; }

    void setFireSpeed(double fireSpeed){ this.fireSpeed = fireSpeed; }
}
