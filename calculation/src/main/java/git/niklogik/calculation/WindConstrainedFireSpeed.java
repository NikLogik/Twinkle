package git.niklogik.calculation;

public class WindConstrainedFireSpeed implements FireSpeed {
    private final FireSpeed fireSpeed;
    private final WindSpeedRatio windSpeedRatio;

    public WindConstrainedFireSpeed(WindSpeedRatio windSpeedRatio, FireSpeed fireSpeed) {
        this.fireSpeed = fireSpeed;
        this.windSpeedRatio = windSpeedRatio;
    }

    public Double computeSpeed(){
        return fireSpeed.computeSpeed() * windSpeedRatio.windRatio();
    }
}
