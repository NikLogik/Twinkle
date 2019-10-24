package ru.nachos.owm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;


/**
 * Class for mapping response about weather from
 * OpenWeatherMap.org
 * JsonIgnoreProperties ignore properties, which not bound with this class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMap implements Serializable {

    private String name;

    private String cod;

    private String message;

    private City city;

    private int cnt;

    private List<Object> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public City getCity() { return city; }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<Object> getList() { return list; }

    public void setList(List<Object> list) { this.list = list; }
}
