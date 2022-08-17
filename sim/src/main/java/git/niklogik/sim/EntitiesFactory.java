package git.niklogik.sim;

import git.niklogik.Position;
import git.niklogik.sim.BaseObject.EntityID;
import git.niklogik.sim.components.ActivityStateComponent;
import git.niklogik.sim.components.ActivityStateComponent.ActivityState;
import git.niklogik.sim.components.NeighbourComponent;
import git.niklogik.sim.components.PositionComponent;
import git.niklogik.sim.components.TransferComponent;

public class EntitiesFactory {
    public SimObject createFireUnit(Position start, double speed, double direction, EntityID left, EntityID right){
        return new BaseObject()
                .addComponent(new PositionComponent(start))
                .addComponent(new TransferComponent(speed, direction))
                .addComponent(new ActivityStateComponent(ActivityState.ACTIVE))
                .addComponent(new NeighbourComponent(left, right));
    }
}
