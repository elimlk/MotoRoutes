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
    private PolylineOptions polyRoutes = new PolylineOptions();

    public Route() {
    }

    public Route(String name,String description, String area, float rank, String difficulty) {
        this.name = name;
        this.description = description;
        this.area = area;
        this.rank = rank;
        this.difficulty = difficulty;
        setPolyOptions("#CC0000FF",10);
    }

/*    public void addRoutePoint(LatLng point) {
        routePoints.add(point);
    }*/

    public void addRoutePoint(LatLng point) {
        polyRoutes.add(point);
    }
    public void setPolyOptions(String color, int width){
        polyRoutes.color( Color.parseColor( "#CC0000FF" ) );
        polyRoutes.width( 10 );
        polyRoutes.visible( true );
    }

    public PolylineOptions getPolyRoutes() {
        return polyRoutes;
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
