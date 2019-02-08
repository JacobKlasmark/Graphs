import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstPaths {
	
	private boolean[] marked;
	private int[] edgeTo;
	private final int s;
	
	private static String[] states = new String[49];
	
	// Checks if the vertex is in the graph
	private void validateVertex (int v)
	{
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException ("vertex " + v + " is not between 0 and " + (V-1));
		
	}
	
	public DepthFirstPaths (Graph G, int s)
	{
		this.s = s;
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		validateVertex(s);
		dfs(G, s);
	}
	
	public void dfs(Graph G, int v)
	{
		marked[v] = true;
		for (int w : G.adj(v))
		{
			if (!marked[w])
			{
				edgeTo[w] = v;
				dfs(G, w);
			}
		}
 	}
	

	public boolean hasPathTo(int v)
	{
		validateVertex(v);
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v)
	{
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x != s; x = edgeTo[x])
		{
			path.push(x);
		}
		path.push(s);
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
		Graph G = new Graph (49);
		//String[] states = new String[49];
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
			G.addEdge(st.get(key), st.get(key2));
		}

		System.out.println("Give a start and an end interval: ");
		System.setIn(in);
		java.util.Scanner input = new java.util.Scanner(System.in);
		String start = (String) input.next();
		String end = (String) input.next();
		
		DepthFirstPaths dfs = new DepthFirstPaths(G, st.get(start));
		
		if (dfs.hasPathTo(st.get(end)))
		{
			StdOut.printf("%s to %s: ",  start, end);
			for (int x : dfs.pathTo(st.get(end)))
			{
				if (x == st.get(start)) StdOut.print(states[x]);
				else					StdOut.print("-" + states[x]);
			}
			StdOut.println();
		}
		else 
		{
			StdOut.printf("%s to %s: not connected ",  start, end);
		}
			
	}
}

/*
Give a start and an end interval: 
DC MO
DC to MO: DC-VA-WV-PA-OH-MI-WI-MN-SD-WY-UT-NV-OR-CA-AZ-NM-TX-OK-MO
*/
