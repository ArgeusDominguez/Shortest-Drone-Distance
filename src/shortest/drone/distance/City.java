/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest.drone.distance;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.LinkedList;

/**
 *
 * @author argeu
 */
public class City implements Comparable<City> {

    private LinkedList<Edge> adjList = new LinkedList<>();
    private Integer pathDistance; 
    private City prev;
    private boolean visited = false;
    private int zipCode;
    private int numPaths = 0;
    private String cityName;
    private String stateName;
    private int timeZone;
    private boolean daylightSavings;
    private double latitude;
    private double longitude;

    public City(int zip, String cName, String sName, double lat, double lon, int zone, boolean daylight) {
        zipCode = zip;
        cityName = cName;
        stateName = sName;
        latitude = lat;
        longitude = lon;
        timeZone = zone;
        daylightSavings = daylight;

    }

    @Override
    public int compareTo(City otherCity) {
        int compare;
        compare = this.cityName.compareTo(otherCity.cityName);
        return compare;
    }

    @Override
    public String toString() {
        return this.cityName + "," + this.stateName;
    }

    public Integer getDistance(City temp) {
        double lat = temp.latitude;
        double lon = temp.longitude;
        double theta = lon - this.longitude;
        double dist = sin(deg2rad(lat)) * sin(deg2rad(this.latitude)) + (cos(deg2rad(lat))
                * cos(deg2rad(this.latitude)) * cos(deg2rad(theta)));
        dist = acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (int) Math.round(dist);
    }
    
    public void setPathDis(int val){
        this.pathDistance = val;
    }
    
    public Integer getPathDis(){
        return this.pathDistance;
    }
    
    public void setPathDisMax(){
        this.pathDistance = Integer.MAX_VALUE;
    }
    
    public void incPathNum(){
        this.numPaths++;
    }
    
    public Integer getPathNum(){
        return this.numPaths;
    }
    
    public void setPrev(City previous){
        this.prev = previous;
    }
    
    public City getPrev(){
        return this.prev;
    }
           
    public LinkedList getList(){
        return this.adjList;
    }

    private double deg2rad(double deg) {
        return deg * (PI / 180);
    }

    private double rad2deg(double rad) {
        return rad * (180 / PI);
    }

    public Double getLat() {
        return this.latitude;
    }

    public Double getLon() {
        return this.longitude;
    }

    public String getZip() {
        return Integer.toString(this.zipCode);
    }

    public String getTimeZone() {

        String time;

        switch (this.timeZone) {

            case -4:
                if (this.daylightSavings) {
                    time = "ADT";
                    break;
                } else {
                    time = "AST";
                    break;
                }
            case -5:
                if (this.daylightSavings) {
                    time = "EDT";
                    break;
                } else {
                    time = "EST";
                    break;
                }
            case -6:
                if (this.daylightSavings) {
                    time = "CDT";
                    break;
                } else {
                    time = "CST";
                    break;
                }
            case -7:
                if (this.daylightSavings) {
                    time = "MDT";
                    break;
                } else {
                    time = "MST";
                    break;
                }
            case -8:
                if (this.daylightSavings) {
                    time = "PDT";
                    break;
                } else {
                    time = "PST";
                    break;
                }
            case -9:
                if (this.daylightSavings) {
                    time = "AKDT";
                    break;
                } else {
                    time = "AKST";
                    break;
                }
            case -10:
                if (this.daylightSavings) {
                    time = "HDT";
                    break;
                } else {
                    time = "HST";
                    break;
                }
            default:
                time = "  ";
                break;

        }

        return time;
    }

}
