package ru.nachos.web.models;
@Deprecated
public class Wind {

    private String formId;

    private String windSpeed;

    private String direction;

    public Wind() {}

    public Wind(String windSpeed, String direction) {
        this.windSpeed = windSpeed;
        this.direction = direction;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
