package git.niklogik.sim.components;

import git.niklogik.calc.Position;
import git.niklogik.sim.Component;

public class PositionComponent implements Component {
    public final Position position;

    public PositionComponent(Position position) {
        this.position = position;
    }
}