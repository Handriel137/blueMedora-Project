/*
 *  Author: Ryan Solnik
 *  Blue Medora Challenge Problem
 */
//model 
package graphjam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GraphApp {

    ArrayList<Integer> visited = new ArrayList<Integer>(12);
    ArrayList<Integer> connections = new ArrayList<Integer>();
    ArrayList names = new ArrayList(12);
    int[][] locations = {
        /*###############################
         ##  0 - Kruthika's abode       ##
         ##  1 - Brian's apartment      ##
         ##  2 - Greg's cass            ##
         ##  3 - Mark's Crib            ##
         ##  4 - Wesley's condo         ##
         ##  5 - Matt's pad             ##
         ##  6 - Bryce's den            ##
         ##  7 - Cam's dwelling         ##
         ##  8 - Mike's digs            ##
         ##  9 - Kirk's farm            ##
         ## 10 - Nathan's flat          ##
         ## 11 - Craig's haunt          ##
         ###############################*/
        //  mapping of nodes and distances between 
        //             0,  1,  2,  3, 4,   5,  6,  7,  8,  9, 10, 11
        /*       0*/{-1, 8, 4, 9, -1, 18, -1, -1, -1, -1, -1, -1},
        /*       1*/ {8, -1, -1, -1, 7, -1, -1, 17, -1, -1, -1, -1},
        /*       2*/ {4, -1, -1, -1, -1, 14, -1, 13, 19, -1, -1, -1},
        /*       3*/ {9, -1, -1, -1, -1, 19, -1, -1, -1, 9, 10, -1},
        /*       4*/ {-1, 7, -1, -1, -1, -1, 6, -1, -1, 10, 11, -1},
        /*       5*/ {18, -1, 14, 19, -1, -1, -1, -1, -1, -1, 15, 14},
        /*       6*/ {-1, -1, -1, -1, 6, -1, -1, -1, 9, -1, -1, 10},
        /*       7*/ {-1, 17, 13, -1, -1, -1, -1, -1, 20, -1, -1, 18},
        /*       8*/ {-1, -1, 19, -1, -1, -1, 9, 20, -1, -1, 12, -1},
        /*       9*/ {-1, -1, -1, 9, 10, -1, -1, -1, -1, -1, 3, -1},
        /*      10*/ {-1, -1, -1, 12, 11, 15, -1, -1, 12, 3, -1, -1},
        /*      11*/ {-1, -1, -1, -1, -1, 14, 10, 18, -1, -1, -1, -1}
    };

    //Constructor with map of locations
    public GraphApp(int start, int end) {
        int startLocation = start;
        int endLocation = end;

        names.add("Kruthika's abode");
        names.add("Brian's apartment");
        names.add("Greg's casa");
        names.add("Mark's Crib");
        names.add("Wesley's condo");
        names.add("Matt's pad");
        names.add("Bryce's den");
        names.add("Cam's dwelling");
        names.add("Mike's digs");
        names.add("Kirk's farm");
        names.add("Nathan's flat");
        names.add("Craig's haunt");

    }

    //creates a list of unchecked nodes.  indexed on location number
    //if location index is set to 0 it has not been checked.
    public void createCheckedLocations(int[][] map) {
        int amount = map.length;
        ArrayList checkedLocation = new ArrayList(amount);
        for (int i = 0; i < amount; i++) {

            checkedLocation.add(0);
        }
        visited = checkedLocation;
    }

    //creates a arrayList of all connecting nodes to requested node.
    //
    public void determineConnections(int locationIndex, int[][] graph, ArrayList connections) {
        int numberOfConnections = graph[locationIndex].length;
        ArrayList connectedPoints = new ArrayList(Collections.nCopies(12, 0));
        connections.clear();
        for (int i = 0; i <= numberOfConnections - 1; i++) {

            //if the distance found is not -1 and the location has not been visited
            if (graph[locationIndex][i] != -1) {

                if ((Integer) visited.get(i) == 0) {
                    //adds the node index to a collection
                    connections.add(i);
                }
            }

        }
    }

    //returns the distance between two nodes based on look up table.
    public int getDistanceBetween(int nodeStart, int nodeEnd, int[][] graph) {

        int distance = 0;
        distance = graph[nodeStart][nodeEnd];

        return distance;
    }

    //method to determine the shortest path between two points on the map.
    public void shortestPath(int startLocation, int targetLocation, int[][] graph) {

        //makes a new array to represent the distance travelled to each point
        int[] pathGraph = new int[12];
        //keeps track if a node is visited or not.
        boolean[] checked = new boolean[12];
        //arrayList to store the path taken to destination
        //ArrayList<int[][]> pairList = new ArrayList<int[][]>();
        ArrayList<Pair<Integer, Integer>> pairList = new ArrayList<Pair<Integer, Integer>>();

        //sets the initial distance to max
        for (int i = 0; i < pathGraph.length; i++) {
            pathGraph[i] = Integer.MAX_VALUE;
            checked[i] = false;
        }

        //resets initial node to 0 distance
        pathGraph[startLocation] = 0;

        //find the shortest path to all connected points
        for (int count = 0; count < pathGraph.length; count++) {
            int currentLocation = minDistanceLocation(pathGraph, checked);

            checked[currentLocation] = true;

            for (int connection = 0; connection < locations.length; connection++) {

                //if the current node is not checked and there is a connection between points and current distance is not @ max value
                //and current distance + edge is less than current distance to node.
                if (!checked[connection] && graph[currentLocation][connection] != -1 && pathGraph[currentLocation] != Integer.MAX_VALUE
                        && pathGraph[currentLocation] + graph[currentLocation][connection] < pathGraph[connection]) {
                    //set the current distance to target node = to current distance at current node + edge value
                    pathGraph[connection] = pathGraph[currentLocation] + graph[currentLocation][connection];
                    Pair<Integer, Integer> nodeConnection = new Pair(currentLocation, connection);
                    pairList.add(nodeConnection);

                }
            }
        }
        printSolution(pathGraph, startLocation, targetLocation);
        determinePath(pairList);
    }

    //finds the next location with the smallest distance
    public int minDistanceLocation(int[] dist, boolean[] pathSet) {
        int min = Integer.MAX_VALUE;
        int minIndex = 0;

        for (int v = 0; v < locations.length; v++) {
            if (pathSet[v] == false && dist[v] < min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    public void printSolution(int[] dist, int startLocation, int endLocation) {

        System.out.println("Total distance from " + names.get(startLocation) + " to " + names.get(endLocation) + "/n");
        System.out.println("= " + dist[endLocation]);

    }

    public ArrayList determinePath(ArrayList<Pair<Integer, Integer>> pairList) {
        //declaring variables
        int location = (int) pairList.get(0).getElement0();
        int connection = (int) pairList.get(0).getElement1();
        int numOfElements;

        ArrayList<Integer> optimalPath = new ArrayList<>();
        ArrayList<Integer> step = new ArrayList<>();
        ArrayList<ArrayList<Integer>> totalPaths = new ArrayList<>();

        step.add(location);
        step.add(connection);
        totalPaths.add(step);

        
        for (int counter = 0; counter <= pairList.size(); counter++) {
            location = (int) pairList.get(counter).getElement0();
            connection = (int) pairList.get(counter).getElement1();
            
            //only increment totalPath counter if item is added to list.
            //determines if a new path is taken
            if (location == totalPaths.get(counter).get(0)) {
                totalPaths.add(new ArrayList<Integer>(Arrays.asList(location, connection)));

            } //else if (location == totalPaths.get(counter).get(numOfElements)) {
             
        else {
                //do nothing
                int counter2 = counter;
            }
        }

        return optimalPath;

    }
}
