package sxj180002; /** Breadth-first search: Object-oriented version
 *  @author rbk
 *  Version 1.0: 2018/10/16
 */

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFSOO extends Graph.GraphAlgorithm<BFSOO.BFSVertex> {
	public static final int INFINITY = Integer.MAX_VALUE;
	Graph.Vertex src;

	// Class to store information about vertices during BFS
	public static class BFSVertex implements Graph.Factory {
		boolean seen;
		Graph.Vertex parent;
		int distance;  // distance of vertex from source
		public BFSVertex(Graph.Vertex u) {
			seen = false;
			parent = null;
			distance = INFINITY;
		}
		public BFSVertex make(Graph.Vertex u) { return new BFSVertex(u); }
	}

	// code to initialize storage for vertex properties is in GraphAlgorithm class
	public BFSOO(Graph g) {
		super(g, new BFSVertex(null));
	}


	// getter and setter methods to retrieve and update vertex properties
	public boolean getSeen(Graph.Vertex u) {
		return get(u).seen;
	}

	public void setSeen(Graph.Vertex u, boolean value) {
		get(u).seen = value;
	}

	public Graph.Vertex getParent(Graph.Vertex u) {
		return get(u).parent;
	}

	public void setParent(Graph.Vertex u, Graph.Vertex p) {
		get(u).parent = p;
	}

	public int getDistance(Graph.Vertex u) {
		return get(u).distance;
	}

	public void setDistance(Graph.Vertex u, int d) {
		get(u).distance = d;
	}

	public void initialize(Graph.Vertex src) {
		for(Graph.Vertex u: g) {
			setSeen(u, false);
			setParent(u, null);
			setDistance(u, INFINITY);
		}
		setDistance(src, 0);
	}

	public void setSource(Graph.Vertex src) {
		this.src = src;
	}

	public Graph.Vertex getSource() {
		return this.src;
	}

	// Visit a node v from u
	void visit(Graph.Vertex u, Graph.Vertex v) {
		setSeen(v, true);
		setParent(v, u);
		setDistance(v, getDistance(u)+1);
	}

	public void bfs(Graph.Vertex src) {
		setSource(src);
		initialize(src);

		Queue<Graph.Vertex> q = new LinkedList<>();
		q.add(src);
		setSeen(src, true);

		while(!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for(Graph.Edge e: g.incident(u)) {
				Graph.Vertex v = e.otherEnd(u);
				if(!getSeen(v)) {
					visit(u,v);
					q.add(v);
				}
			}
		}
	}

	// Run breadth-first search algorithm on g from source src
	public static BFSOO breadthFirstSearch(Graph g, Graph.Vertex src) {
		BFSOO b = new BFSOO(g);
		b.bfs(src);
		return b;
	}

	public static BFSOO breadthFirstSearch(Graph g, int s) {
		return breadthFirstSearch(g, g.getVertex(s));
	}

	public static int diameter(Graph g)
	{
		BFSOO b = breadthFirstSearch(g, 1);
		Graph.Vertex maxDistanceVertex = null;
		int maxDistance=0;
		for(Graph.Vertex u: g) {
				if(b.getDistance(u)>maxDistance && b.getDistance(u) != INFINITY)
				{
					maxDistance=b.getDistance(u);
					maxDistanceVertex = u;
				}
			}
		b = breadthFirstSearch(g,maxDistanceVertex);
		maxDistance = 0;
		for(Graph.Vertex u: g) {
			if(b.getDistance(u)>maxDistance && b.getDistance(u) != INFINITY)
			{
				maxDistance=b.getDistance(u);
			}
		}
		return maxDistance;
	}

	public static void main(String[] args) throws Exception {
		String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   7 6 -1 1";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
		// Read graph from input
		Graph g = Graph.readGraph(in, false);
		g.printGraph(false);
		int diameter = diameter(g);
		System.out.println("\nDiameter of rbk.Graph is: " + diameter);
	}
}

/* Sample run:
______________________________________________
rbk.Graph: n: 7, m: 8, directed: true, Edge weights: false
1 :  (1,2) (1,3)
2 :  (2,4)
3 :  (3,4)
4 :  (4,5)
5 :  (5,1)
6 :  (6,7)
7 :  (7,6)
______________________________________________
Output of BFS:
Node	Dist	Parent
----------------------
1	0	null
2	1	1
3	1	1
4	2	2
5	3	4
6	Inf	--
7	Inf	--
*/
