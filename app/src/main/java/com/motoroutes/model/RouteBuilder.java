package com.motoroutes.model;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class RouteBuilder {

    private ArrayList<MyLocation> myLocations;

    public RouteBuilder() {
        this.myLocations = new ArrayList<MyLocation>();
    }

    public ArrayList<MyLocation> parseGpxToArray(String filePath) throws FileNotFoundException {
        ArrayList<MyLocation> gpxList;
        if(filePath !=null){
            File gpxFile = new File(filePath);
            gpxList = decodeGPX(filePath);
        }
        else
            gpxList = null;
        return gpxList;
    }

    private ArrayList<MyLocation> decodeGPX(String file) {
        ArrayList<MyLocation> list = new ArrayList<MyLocation>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileInputStream fileInputStream = new FileInputStream(file);
            Document document = documentBuilder.parse(fileInputStream);
            Element elementRoot = document.getDocumentElement();

            NodeList nodelist_trkpt = elementRoot.getElementsByTagName("trkpt");

            for (int i = 0; i < nodelist_trkpt.getLength(); i++) {

                Node node = nodelist_trkpt.item(i);
                NamedNodeMap attributes = node.getAttributes();

                String newLatitude = attributes.getNamedItem("lat").getTextContent();
                Double newLatitude_double = Double.parseDouble(newLatitude);

                String newLongitude = attributes.getNamedItem("lon").getTextContent();
                Double newLongitude_double = Double.parseDouble(newLongitude);

                MyLocation newLocation = new MyLocation(newLatitude_double, newLongitude_double);
                list.add(newLocation);

            }

            fileInputStream.close();

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public PolylineOptions createPolygon(ArrayList<MyLocation> myLocations) {
        if (myLocations==null)
            return null;
        PolylineOptions polyRoutes = new PolylineOptions();
        for (MyLocation myLocation : myLocations){
            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            polyRoutes.add(latLng);
        }
        polyRoutes.color( Color.parseColor( "#CC0000FF" ) );
        polyRoutes.width( 10 );
        polyRoutes.visible( true );
        return polyRoutes;
    }

}
