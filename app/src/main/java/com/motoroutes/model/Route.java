package com.motoroutes.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Route {
    private String name;
    private String description;
    private String area;
    private float rank;
    private String difficulty;
    private ArrayList<MyLocation> myLocations;

    public Route() {
    }

    public Route(String name, String description, String area, float rank, String difficulty) {
        this.name = name;
        this.description = description;
        this.area = area;
        this.rank = rank;
        this.difficulty = difficulty;
        this.myLocations = new ArrayList<MyLocation>();
    }

    public void addRoutePoint(MyLocation point) {
        myLocations.add(point);
    }

    public ArrayList<MyLocation> getPointsRoutes() {
        return myLocations;
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }

    public float getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setMyLocations(ArrayList<MyLocation> myLocations) {
        this.myLocations = myLocations;
    }

    public ArrayList<MyLocation> getMyLocations() {
        return myLocations;
    }
}
