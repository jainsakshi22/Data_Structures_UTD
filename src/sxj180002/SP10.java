/**
 * authors Pooja Srinivasan and Sakshi Jain 10/28/18
 */
package sxj180002;

import sxj180002.Graph.Vertex;
import sxj180002.Graph.Edge;
import sxj180002.Graph.GraphAlgorithm;
import sxj180002.Graph.Factory;
import sxj180002.Graph.Timer;

import java.io.File;
import java.util.*;

public class SP10 extends GraphAlgorithm<SP10.DFSVertex> {
    private static List<Vertex> topoOrderVertices = new LinkedList<>();
    private static int cno;

    /**
     * Class to store information about vertices during DFS
     */
    public static class DFSVertex implements Factory {
        int color; // 0 - white(not visited), 1 - grey(visiting or node part of recursion stack), 2 - black(visited)
        Vertex parent;
        int compNo;

        public DFSVertex(Vertex u) {
            color = 0;
            parent = null;
            compNo = 0;
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }


    /**
     * code to initialize storage for vertex properties is in GraphAlgorithm class
     */
    public SP10(Graph g) {
        super(g, new DFSVertex(null));
        cno = 0;
    }


    /**
     * performs DFS on graph g and assigns component number for each connected component
     * by calling helper method dfsVisitAndIsDAG, throws Exception and return null if there is cycle else populates topoplogically
     * ordered vertices.
     *
     * @param g
     * @return
     */
    public static SP10 depthFirstSearch(Graph g) {
        SP10 d = new SP10(g);
        for (Vertex v : g) {
            // if node is not visited
            if (d.get(v).color == 0) {
                try {
                    d.dfsVisitAndIsDAG(g, v);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
        return d;
    }

    public static SP10 stronglyConnectedComponents(Graph g) {
        SP10 d = new SP10(g);
        for (Vertex v : g) {
            // if node is not visited
            if (d.get(v).color == 0) {
                try {
                    d.dfsVisitAndIsDAG(g, v, ++cno);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
        return d;
    }

    /**
     * checks cycle by using color attribute by checking if there is a reference to a node i recurrence stack and throws Exception
     * if no cycle, adds the vertices in order of finishing time to result topological vertices.
     *
     * @param g
     * @param u
     * @throws Exception
     */
    private void dfsVisitAndIsDAG(Graph g, Vertex u) throws Exception {
        get(u).color = 1;
        for (Edge e : g.incident(u)) {
            Vertex v = e.otherEnd(u);
            if (get(v).color == 0) {
                get(v).parent = u;
                dfsVisitAndIsDAG(g, v);
            } else if (get(v).color == 1) { // indicates loop
                throw new Exception("Cycle Detected. Cannot perform topological sorting");
            } else if (get(v).color == 2) {  // back edge
                continue;
            }
        }
        topoOrderVertices.add(0, u);
        get(u).color = 2;
    }

    private void dfsVisitAndIsDAG(Graph g, Vertex u, int cno) throws Exception {
        get(u).color = 1;
        get(u).compNo = cno;
        for (Edge e : g.incident(u)) {
            Vertex v = e.otherEnd(u);
            if (get(v).color == 0) {
                get(v).parent = u;
                dfsVisitAndIsDAG(g, v, cno);
            } else if (get(v).color == 1) { // indicates loop
                throw new Exception("Cycle Detected. Cannot perform topological sorting");
            } else if (get(v).color == 2) {  // back edge
                continue;
            }
        }
        topoOrderVertices.add(0, u);
        get(u).color = 2;
    }

    /**
     * Member function to find topological order
     */
    public List<Vertex> topologicalOrder1() {
        SP10 d = depthFirstSearch(this.g);
        if (d == null) return null;  // graph contains cycle
        return topoOrderVertices;
    }

//    public static SP10 stronglyConnectedComponents() {
//        SP10 d = depthFirstSearch(this.g);
//        if (d == null) return null;  // graph contains cycle
//        return d;
//    }


    /**
     * Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    /**
     * Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
     *
     * @param g
     * @return
     */
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    /**
     * driver method optional
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String string = "7 7   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   1 5 7   6 7 1";
       // string = "7 7   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        SP10 d = new SP10(g);
        List<Vertex> orderedVertex = d.topologicalOrder1();
        System.out.println("Topological Ordering Using DFS:");
        if (orderedVertex == null) {
            System.out.println("Cycle detected in DFS. Cannot perform Topological Sort");
        } else {
            for (Vertex u : orderedVertex) {
                System.out.print(u.getName() + " ");
            }
        }

        System.out.println();

    }
}
