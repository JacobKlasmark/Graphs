import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DijkstraSP 
{
	private static String[] states = new String[49];
	
	private double[] distTo;
	private Edge[] edgeTo;
	private IndexMinPQ<Double> pq;
	
	public DijkstraSP(EdgeWeightedGraph G, int s)
	{
		distTo = new double [G.V()];
		edgeTo = new Edge[G.V()];
		
		validateVertex(s);
		
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;
		
		// relax vertices in order of distance from s
		pq = new IndexMinPQ<Double>(G.V());
		pq.insert(s,  distTo[s]);
		while(!pq.isEmpty())
		{
			int v = pq.delMin();
			for (Edge e : G.adj(v))
				relax(e, v);
		}
	}
	
	private void relax (Edge e, int v)
	{
		int w = e.other(v);
		if (distTo[w] > distTo[v] + e.weight())
		{
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			if (pq.contains(w))	pq.decreaseKey(w, distTo[w]);
			else				pq.insert(w, distTo[w]);
		}
	}
	
	//Returns shortest path between a vertex and the source vertex
	public double distTo(int v)
	{
		validateVertex(v);
		return distTo[v];
	}
	
	public boolean hasPathTo(int v)
	{
		validateVertex(v);
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	
	private void validateVertex(int v)
	{
		int V = distTo.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
	
	public Iterable<Edge> pathTo(int v)
	{
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Stack<Edge> path = new Stack<Edge>();
		int x = v;
		for (Edge e = edgeTo[v]; e != null; e = edgeTo[x])
		{
			path.push(e);
			x = e.other(x);
		}
		return path;
		
	}
	
	public static void main (String[] args) throws FileNotFoundException
	{
		InputStream in = System.in;
		System.setIn(new FileInputStream("States.txt"));
		
		// Creates hashtable where every state has unique index
		// Create an array with the key/index reversed from the hashtable
		// Add the edges to the graph
		SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
		EdgeWeightedGraph G = new EdgeWeightedGraph (49);
		double weight = 1;
		int i = 0;
		while (!StdIn.isEmpty())
		{
			String key = StdIn.readString();
			if (!st.contains(key))
			{
				st.put(key,  i);
				states[i] = key;
				i++;
			}
			String key2 = StdIn.readString();
			if (!st.contains(key2))
			{
				st.put(key2,  i);
				states[i] = key2;
				i++;
			}
			Edge e = new Edge (st.get(key), st.get(key2), weight);
			weight++;
			G.addEdge (e);
			
		}
		
		System.out.println("Give a start and an end interval: ");
		System.setIn(in);
		java.util.Scanner input = new java.util.Scanner(System.in);
		String start = (String) input.next();
		String end = (String) input.next();
		
		DijkstraSP sp = new DijkstraSP (G, st.get(start));
		
		if (sp.hasPathTo(st.get(end)))
		{
			StdOut.printf("%s to %s (%.2f) ",  start, end, sp.distTo(st.get(end)));
			for (Edge e : sp.pathTo(st.get(end)))
			{
				
				StdOut.print(e.toString(states) + "  ");
			}
			StdOut.println();
		}
		else
		{
			StdOut.printf("%s to %S   no path\n", start, end);
		}
		
		
				
		
	
	}

}

/*
Give a start and an end interval: 
AL WV
AL to WV (124.00) AL-TN 4,00  KY-TN 59,00  KY-WV 61,00 
*/