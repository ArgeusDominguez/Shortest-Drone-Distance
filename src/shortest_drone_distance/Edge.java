/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest_drone_distance;

/**
 *
 * @author argeu
 */
public class Edge {
    
    private Integer weight;
    private City city;
    
    public Edge(City c, int tWeight){
        city = c;
        weight = tWeight;
    }
    
    public City getCity(){
        return this.city;
    }
    
    public int getWeight(){
        return this.weight;
    }
    
    @Override 
    public String toString(){
        return this.city.toString() + " is " + this.weight.toString() + " miles away. ";
    }
    
}
