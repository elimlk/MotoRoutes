package com.motoroutes.model;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String name;
    private String description;
    private String area;
    private float rank;
    private String difficulty;
    private ArrayList<Location> locations;

    public Route() {
    }

    public Route(String name,String description, String area, float rank, String difficulty) {
        this.name = name;
        this.description = description;
        this.area = area;
        this.rank = rank;
        this.difficulty = difficulty;
        this.locations = new ArrayList<Location>();
    }



    public void addRoutePoint(Location point) {
        locations.add(point);
    }

    public ArrayList<Location> getPointsRoutes() {
        return locations;
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

    public String getDifficulty() {
        return difficulty;
    }
}
