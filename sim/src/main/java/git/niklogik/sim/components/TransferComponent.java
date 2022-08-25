package git.niklogik.sim.components;

import git.niklogik.sim.Component;

/**
 * Component contains transfer properties:
 * {@link TransferComponent#direction},
 * {@link TransferComponent#speed}
 */
public class TransferComponent implements Component {
    public final Double speed;
    public final Double direction;

    public TransferComponent(Double speed, Double direction) {
        this.speed = speed;
        this.direction = direction;
    }
}
