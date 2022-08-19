package git.niklogik.sim.components;

import git.niklogik.sim.Component;
import git.niklogik.sim.entities.EntityID;

public class NeighbourComponent implements Component {
    public final EntityID left;
    public final EntityID right;

    public NeighbourComponent(EntityID left, EntityID right) {
        this.left = left;
        this.right = right;
    }
}
