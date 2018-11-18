/**
 * authors Sakshi Jain(sxj180002) and Pooja Srinivasan(pxs176230)
 */
package sxj180002;

import sxj180002.Graph.Vertex;
import sxj180002.Graph.Edge;
import sxj180002.Graph.GraphAlgorithm;
import sxj180002.Graph.Factory;
import sxj180002.Graph.Timer;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    private int top;
    private int cno;
    private boolean isReversed;
    private List<Vertex> vertices;

    public int cno(Vertex v) {
        return get(v).cno;
    }

    /**
     * Class to store information about vertices during DFS
     */
    public static class DFSVertex implements Factory {
        int color; // 0 - white(not visited), 1 - grey(visiting or node part of recursion stack), 2 - black(visited)
        Vertex parent;
        int cno;
        int top;

        public DFSVertex(Vertex u) {
            color = 0;
            parent = null;
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    /**
     * code to initialize storage for vertex properties is in GraphAlgorithm class
     */
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        this.cno = 0;
        this.isReversed = false;
        this.vertices = new LinkedList<>();
        this.top = g.size();
    }

    public DFS depthFirstSearch() {
        return depthFirstSearch(g);
    }

    /**
     * performs DFS on graph g and assigns component number for each connected component
     * by calling helper method dfsVisitAndIsDAG, throws Exception and return null if there is cycle else populates topoplogically
     * ordered vertices.
     *
     * @param g
     * @return
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.cno = 0;
        for (Vertex v : g) {
            // if node is not visited
            if (d.get(v).color == 0) {
                try {
                    d.dfsVisitAndIsDAG(g, v, ++d.cno);
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
    private void dfsVisitAndIsDAG(Graph g, Vertex u, int c) throws Exception {
        System.out.println(u.name);
        get(u).color = 1;
        get(u).cno = c;
        Iterable<Edge> iter = isReversed ? g.incident(u) : g.outEdges(u);
        for (Edge e : iter) {
            Vertex v = isReversed ? e.fromVertex() : e.toVertex();
            if (get(v).color == 0) {
                get(v).parent = isReversed ? e.toVertex() : e.fromVertex();
                dfsVisitAndIsDAG(g, v, c);
            } else {
                continue;
            }
        }
        get(u).top = top--;
        vertices.add(0, u);
        get(u).color = 2;
    }

    /**
     * <p> This method traverses the graph from a fixed source</p>
     * @param list Lidt of vertices traversed while running DFS for first time
     * @return DFS instance
     */
    private DFS dfs(List<Vertex> list) {
        DFS d = new DFS(g);
        for (Vertex v : list) {
            get(v).color = 0;
            get(v).parent = null;
            get(v).top = 0;
        }
        d.vertices = new LinkedList<>();
        cno = 0;
        for (Vertex v : list) {
            if (get(v).color == 0) {
                try {
                    dfsVisitAndIsDAG(g, v, ++cno);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return d;
    }

    /**
     * <p> Member function to find topological order </p>
     * @return
     */
    public List<Vertex> topologicalOrder1() {
        DFS d = depthFirstSearch(this.g);
        if (d == null) return null;  // graph contains cycle
        return d.vertices;
    }

    /**
     * Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    /**
     * <p> Get the strongly connected components of graph g</p>
     * @param g Graph
     * @return DFS instance
     */
    public static DFS stronglyConnectedComponents(Graph g) {
        DFS dfs = new DFS(g);
        dfs = dfs.depthFirstSearch();
        List<Vertex> vertexList = new LinkedList<>(dfs.vertices);
        g.reverseGraph();
        dfs.isReversed = true;
        dfs.vertices = new LinkedList<>();
        dfs.dfs(vertexList);
        g.reverseGraph();
        dfs.isReversed = false;
        return dfs;
    }

    /**
     * <p>Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG</p>
     *
     * @param g
     * @return
     */
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    /**
     * <p>Driver method optional</p>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //String string = "5 6   1 2 1   1 4 1   2 3 1   2 4 1   4 3 1   3 5 1";
        String string = "11 17   1 11 1   11 3 1   11 4 1   11 6 1   6 3 1   10 6 1   3 10 1   2 3 1   2 7 1   7 8 1   5 7 1   5 8 1   5 4 1   8 2 1   4 9 1   4 1 1   9 11 1";//directed graph with cycle
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        DFS d = stronglyConnectedComponents(g);
        System.out.println("Vertex  ComponentNo:\n");
        for (Vertex vertex : d.vertices) {
            System.out.println(vertex.getName() + "   :    " + d.cno(vertex));
        }
        System.out.println("\nNumber of strongly connected components in the graph: " + d.cno);

    }
}
