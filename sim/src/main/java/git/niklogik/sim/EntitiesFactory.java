package git.niklogik.sim;

import git.niklogik.Position;
import git.niklogik.sim.components.*;
import git.niklogik.sim.components.ActivityStateComponent.ActivityState;
import git.niklogik.sim.entities.BaseEntity;
import git.niklogik.sim.entities.EntityID;

public class EntitiesFactory {
    public Entity createFireUnit(Position start, double speed, double direction, EntityID left, EntityID right){
        return new BaseEntity()
                .addComponent(new PositionComponent(start))
                .addComponent(new TransferComponent(speed, direction))
                .addComponent(new ActivityStateComponent(ActivityState.ACTIVE))
                .addComponent(new NeighbourComponent(left, right));
    }

    public Entity createWeather(double speed, double direction, double humidity, double temperature){
        return new BaseEntity()
                .addComponent(new TransferComponent(speed, direction))
                .addComponent(new EnvStateComponent(humidity, temperature));
    }
}
