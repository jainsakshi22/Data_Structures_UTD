/**
 * author: Sakshi Jain (sxj180002) and Harsh Verma
 */
package sxj180002;

import rbk.BFSOO;
import rbk.Graph;
import rbk.Graph.Vertex;

import java.io.File;
import java.util.Scanner;

public class SP12 {

    // source vertex
    private Vertex src;

    // get source vertex
    public Vertex getSrc() {
        return src;
    }

    // assign source vertex
    public void setSrc(Vertex src) {
        this.src = src;
    }

    /**
     * Calculates diameter of connected acyclic graph
     * @param g Graph
     * @return
     */
    int diameter(Graph g) {
        if (g.size() == 0) return 0;
        // set source vertex
        setSrc(g.getVertex(g.size()));

        // maxNode is the node which has the maximum distance from source vertex
        Vertex maxDistanceNode = null;
        int distance = Integer.MIN_VALUE;

        // calls bfs with source vertex defined in the driver function.
        BFSOO bfsoo = BFSOO.breadthFirstSearch(g, getSrc());

        for (Vertex u : g) {
            if (bfsoo.getDistance(u) > distance) {
                maxDistanceNode = u;
                distance = bfsoo.getDistance(u);
            }
        }

        // assigns maxNode as the source vertex
        setSrc(maxDistanceNode);

        //calls bfs twice with new source vertex
        bfsoo = BFSOO.breadthFirstSearch(g, getSrc());

        distance = Integer.MIN_VALUE;
        for (Vertex u : g) {
            if (bfsoo.getDistance(u) > distance) {
                maxDistanceNode = u;
                distance = bfsoo.getDistance(u);
            }
        }
        // now distance of maxNode from source vertex will give the diameter of the graph.
        return bfsoo.getDistance(maxDistanceNode);
    }

    public static void main(String[] args) throws Exception {
        String string = "10 9   1 2 2   1 3 3   2 4 5   2 5 4   3 6 1   3 7 1   4 8 1   7 9 1   7 10 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readGraph(in, false);
        g.printGraph(false);

        SP12 sp12 = new SP12();

        int diameter = sp12.diameter(g);
        System.out.println("Diameter of tree: " + diameter);
    }
}
