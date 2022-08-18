package git.niklogik.sim;

import git.niklogik.Position;
import git.niklogik.sim.BaseObject.EntityID;
import git.niklogik.sim.components.*;
import git.niklogik.sim.components.ActivityStateComponent.ActivityState;

public class EntitiesFactory {
    public SimObject createFireUnit(Position start, double speed, double direction, EntityID left, EntityID right){
        return new BaseObject()
                .addComponent(new PositionComponent(start))
                .addComponent(new TransferComponent(speed, direction))
                .addComponent(new ActivityStateComponent(ActivityState.ACTIVE))
                .addComponent(new NeighbourComponent(left, right));
    }

    public SimObject createWeather(double speed, double direction, double humidity, double temperature){
        return new BaseObject()
                .addComponent(new TransferComponent(speed, direction))
                .addComponent(new EnvStateComponent(humidity, temperature));
    }
}
