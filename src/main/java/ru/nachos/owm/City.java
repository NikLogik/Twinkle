package ru.nachos.owm;

public class City {

    private long id;

    private String name;

    private Coord coord;

    private String country;

    private int timezone;

    private long sunrise;

    private long sunset;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public long getSunrise() { return sunrise; }

    public void setSunrise(long sunrise) { this.sunrise = sunrise; }

    public long getSunset() { return sunset;}

    public void setSunset(long sunset) { this.sunset = sunset;}

    static class Coord{

        private double lat;

        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}
