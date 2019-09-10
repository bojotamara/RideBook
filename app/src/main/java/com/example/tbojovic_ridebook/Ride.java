package com.example.tbojovic_ridebook;

import java.time.LocalDate;
import java.time.Duration;
import java.util.List;

public class Ride {
    private LocalDate date; // this is immutable
    private Duration time;
    private double distance;
    private int averageCadence;
    private String comment;

    public Ride(LocalDate date, Duration time, double distance, int averageCadence) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.averageCadence = averageCadence;
        this.comment = "";
    }

    public Ride(LocalDate date, Duration time, double distance, int averageCadence, String comment) {
        this(date, time, distance, averageCadence );
        this.comment = comment;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Duration getTime() {
        return this.time;
    }

    public void setTime(Duration time) {
        this.time = time;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double dist) {
        this.distance = dist;
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

    public double getAverageSpeed() {
        double hours = this.time.toMinutes()/60.0;
        return this.distance/hours;
    }

    static double totalDistance(List<Ride> rides) {
        double total = 0;
        for (Ride ride : rides) {
            total += ride.getDistance();
        }
        return total;
    }

}
