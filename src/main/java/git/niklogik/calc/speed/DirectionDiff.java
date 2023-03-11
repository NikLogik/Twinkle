package git.niklogik.calc.speed;

public class DirectionDiff {
    private final Double fireDirection;
    private final Double windDirection;

    public DirectionDiff(Double windDirection, Double targetDirection) {
        this.fireDirection = targetDirection;
        this.windDirection = windDirection;
    }

    public Double difference() {
        return windDirection - fireDirection;
    }
}
