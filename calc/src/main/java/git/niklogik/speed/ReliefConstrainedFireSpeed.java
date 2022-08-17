package git.niklogik.speed;

public class ReliefConstrainedFireSpeed implements ComputeFireSpeed {
    private final ComputeFireSpeed fireSpeed;
    private final ReliefSpeedRatio speedRatio;

    public ReliefConstrainedFireSpeed(ReliefSpeedRatio speedRatio, ComputeFireSpeed fireSpeed) {
        this.fireSpeed = fireSpeed;
        this.speedRatio = speedRatio;
    }

    @Override
    public Double computeSpeed() {
        return fireSpeed.computeSpeed() * speedRatio.reliefRatio();
    }
}
