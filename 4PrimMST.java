import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PrimMST 
{
	private static String[] states = new String[49];

	private Edge[] edgeTo;
	private double[] distTo;
	private boolean[] marked;
	private IndexMinPQ<Double> pq;
	
	public PrimMST(EdgeWeightedGraph G)
	{
		edgeTo = new Edge[G.V()];
		distTo = new double [G.V()];
		marked = new boolean[G.V()];
		
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		pq = new IndexMinPQ<Double>(G.V());
		distTo[0] = 0.0;
		pq.insert(0,  0.0);
		while (!pq.isEmpty())
			visit(G, pq.delMin());
	}
	
	

	private void visit(EdgeWeightedGraph G, int v)
	{ //Add v to tree; update data structure
		marked[v] = true;
		for (Edge e : G.adj(v))
		{
			int w = e.other(v);
			if (marked[w]) continue;
			if (e.weight() < distTo[w])
			{ // Edge e is new best connection from tree to w
				edgeTo[w] = e;
				distTo[w] = e.weight();
				if (pq.contains(w))	pq.changeKey(w, distTo[w]);
				else				pq.insert(w, distTo[w]);
			}
		}
	}
	public Iterable<Edge> edges()
	{
		Queue<Edge> mst = new Queue<Edge>();
		for (int v = 0; v < edgeTo.length; v++)
		{
			Edge e = edgeTo[v];
			if (e != null)
			{
				mst.enqueue(e);
			}
		}
		return mst;
	}
	
	public double weight()
	{
		double weight = 0.0;
		for (Edge e : edges())
			weight += e.weight();
		return weight;
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
		
		/*System.out.println();
		System.out.println("Give a start and an end interval: ");
		System.setIn(in);
		java.util.Scanner input = new java.util.Scanner(System.in);
		String start = (String) input.next();
		String end = (String) input.next();*/
		
		PrimMST mst = new PrimMST (G);
		for (Edge e : mst.edges())
		{
			StdOut.println(e.toString(states));
		}
		StdOut.printf("%.5f\n", mst.weight());
		
	}
		

}

/*
AL-FL 1,00
AL-GA 2,00
AL-MS 3,00
AL-TN 4,00
AR-MS 7,00
AR-LA 5,00
AR-MO 6,00
AR-OK 8,00
AR-TX 10,00
AZ-NM 12,00
AZ-CA 11,00
CO-NM 19,00
AZ-NV 13,00
AZ-UT 14,00
CA-OR 16,00
CO-OK 20,00
CO-KS 17,00
CO-NE 18,00
CO-WY 22,00
CT-NY 24,00
CT-MA 23,00
NJ-NY 91,00
CT-RI 25,00
DC-VA 27,00
DC-MD 26,00
KY-VA 60,00
DE-MD 28,00
DE-NJ 29,00
DE-PA 30,00
GA-NC 32,00
GA-SC 33,00
IA-MO 37,00
IA-IL 35,00
IA-MN 36,00
IA-SD 39,00
IA-WI 40,00
ID-NV 42,00
ID-MT 41,00
ID-WA 45,00
IL-IN 47,00
IL-KY 48,00
IN-MI 52,00
IN-OH 53,00
KY-WV 61,00
MA-NH 64,00
MA-VT 67,00
ME-NH 71,00
MN-ND 74,00
1488.00000
*/
