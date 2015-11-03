/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphjam;

/**
 *
 * @author Ryan Solnik
 */


public class GraphJam {

    public static void main(String[] args) {
        GraphApp map = new GraphApp(0, 11);

        map.createCheckedLocations(map.locations);
        map.determineConnections(0, map.locations, map.connections);

       map.shortestPath(0,11, map.locations);
       
    }

}
