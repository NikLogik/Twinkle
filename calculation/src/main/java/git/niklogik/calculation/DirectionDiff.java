package git.niklogik.calculation;

public class DirectionDiff {
    private final Double fireDirection;
    private final Double windDirection;

    public DirectionDiff(Double fireDirection, Double windDirection) {
        this.fireDirection = fireDirection;
        this.windDirection = windDirection;
    }

    public Double difference() {
        return windDirection - fireDirection;
    }
}
