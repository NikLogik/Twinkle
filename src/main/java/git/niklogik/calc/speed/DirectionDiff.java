package git.niklogik.calc.speed;

public class DirectionDiff {
    private final Double targetDirection;
    private final Double windDirection;

    public DirectionDiff(Double windDirection, Double targetDirection) {
        this.targetDirection = targetDirection;
        this.windDirection = windDirection;
    }

    public Double difference() {
        return windDirection - targetDirection;
    }
}
