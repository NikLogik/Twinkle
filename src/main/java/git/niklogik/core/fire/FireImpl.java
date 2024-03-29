package git.niklogik.core.fire;

import git.niklogik.core.Id;
import git.niklogik.core.fire.lib.Agent;
import git.niklogik.core.fire.lib.Fire;
import git.niklogik.core.fire.lib.FireFactory;
import org.locationtech.jts.geom.Coordinate;
import git.niklogik.core.config.lib.Config;
import git.niklogik.core.utils.AgentMap;

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
     * Главное направление распространение огня (соответствует направлению скорости ветра)
     */
    private double headDirection;
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
    private AgentMap twinkles = new AgentMap();
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
    public double getHeadDirection() { return headDirection; }
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
    public AgentMap getTwinkles() { return twinkles; }
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
    public Agent removeAgent(Agent agent){ return twinkles.remove(agent.getId()); }
    @Override
    public Agent removeAgent(Id<Agent> id){ return twinkles.remove(id); }
    @Override
    public void setCenterPoint(Coordinate center){
        this.center = center;
    }
    @Override
    public void setFireSpeed(double fireSpeed){ this.fireSpeed = fireSpeed; }
    void setHeadDirection(double direction){ this.headDirection = direction; }
}
