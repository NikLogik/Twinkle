package git.niklogik.speed;

public class DirectionSpeedRatio {
    private final AlbineWindSpeed albineWindSpeed;

    public DirectionSpeedRatio(AlbineWindSpeed albineWindSpeed) {
        this.albineWindSpeed = albineWindSpeed;
    }

    public Double directionRatio(DirectionDiff direction){
        double albineSpeed = albineWindSpeed.computeSpeed();
        double A = 0.785 * albineSpeed - 0.106 * Math.pow(albineSpeed, 2);
        return Math.exp(A * (Math.cos(Math.toRadians(direction.difference())) - 1));
    }
}
