package com.huertix.gavines.models;

import java.util.Objects;

public class Event {

    private int id;
    private String place;
    private String date;
    private Double humedity;
    private Double temperature;

    public Event() {
        this.place = "";
        this.date = "";
        this.humedity = 0.0;
        this.temperature = 0.0;
    }

    public Event(String place, String date, Double humedity, Double temperature) {
        this.place = place;
        this.date = date;
        this.humedity = humedity;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setLocation(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getHumedity() {
        return humedity;
    }

    public void setHumedity(Double humedity) {
        this.humedity = humedity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                place == event.place &&
                Objects.equals(date, event.date) &&
                Objects.equals(humedity, event.humedity) &&
                Objects.equals(temperature, event.temperature);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, place, date, humedity, temperature);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", place=" + place +
                ", date='" + date + '\'' +
                ", humedity=" + humedity +
                ", temperature=" + temperature +
                '}';
    }
}
