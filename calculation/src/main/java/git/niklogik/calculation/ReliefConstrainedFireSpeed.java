package git.niklogik.calculation;

public class ReliefConstrainedFireSpeed implements FireSpeed {
    private final FireSpeed fireSpeed;
    private final ReliefSpeedRatio speedRatio;

    public ReliefConstrainedFireSpeed(ReliefSpeedRatio speedRatio, FireSpeed fireSpeed) {
        this.fireSpeed = fireSpeed;
        this.speedRatio = speedRatio;
    }

    @Override
    public Double computeSpeed() {
        return fireSpeed.computeSpeed() * speedRatio.reliefRatio();
    }
}
