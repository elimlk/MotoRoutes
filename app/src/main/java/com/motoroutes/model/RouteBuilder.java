package com.motoroutes.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;



public class RouteBuilder {

    private ArrayList<Location> locations;

    public RouteBuilder() {
        this.locations = new ArrayList<Location>();
    }

    public void parseGpxToArray(String filePath) throws FileNotFoundException {
        try {
            FileInputStream input = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw e;
        };


    }
}
