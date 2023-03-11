package git.niklogik.calc.speed;

public class WindConstrainedFireSpeed implements ComputeFireSpeed {
    private final ComputeFireSpeed fireSpeed;
    private final WindSpeedRatio windSpeedRatio;

    public WindConstrainedFireSpeed(WindSpeedRatio windSpeedRatio, ComputeFireSpeed fireSpeed) {
        this.fireSpeed = fireSpeed;
        this.windSpeedRatio = windSpeedRatio;
    }

    public Double computeSpeed(){
        return fireSpeed.computeSpeed() * windSpeedRatio.windRatio();
    }
}
