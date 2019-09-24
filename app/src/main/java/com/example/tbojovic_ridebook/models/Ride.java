package com.example.tbojovic_ridebook.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Ride implements Serializable {
    private LocalDate date; // this is immutable
    private LocalTime time; // so is this i believe
    private double distance;
    private double averageSpeed;
    private int averageCadence;
    private String comment;

    public Ride(LocalDate date, LocalTime time, double distance, double averageSpeed, int averageCadence) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.averageCadence = averageCadence;
        this.averageSpeed = averageSpeed;
        this.comment = "";
    }

    public Ride(LocalDate date, LocalTime time, double distance, double averageSpeed, int averageCadence, String comment) {
        this(date, time, distance,  averageSpeed , averageCadence);
        this.comment = comment;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double dist) {
        this.distance = dist;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getAverageCadence() {
        return this.averageCadence;
    }

    public void setAverageCadence(int cadence) {
        this.averageCadence = cadence;
    }

    public String getComment() {
        return this.comment; // how to return?
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    static public double totalDistance(List<Ride> rides) {
        double total = 0;
        for (Ride ride : rides) {
            total += ride.getDistance();
        }
        return total;
    }

}
