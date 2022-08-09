package git.niklogik.calculation;

public class DirectionSpeedRatio {
    private final AlbineWindSpeed albineWindSpeed;
    private final Double windSpeed;

    public DirectionSpeedRatio(AlbineWindSpeed albineWindSpeed, Double windSpeed) {
        this.albineWindSpeed = albineWindSpeed;
        this.windSpeed = windSpeed;
    }

    public Double directionRatio(Double direction){
        double albineSpeed = albineWindSpeed.computeSpeed(windSpeed);
        double A = 0.785 * albineSpeed - 0.106 * Math.pow(albineSpeed, 2);
        return Math.exp(A * (Math.cos(Math.toRadians(direction)) - 1));
    }
}
