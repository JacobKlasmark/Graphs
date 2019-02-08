import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DirectedCycle 
{
	private static String[] states = new String[50];
	
	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle;
	private boolean[] onStack;
	
	public DirectedCycle(Digraph G)
	{
		onStack = new boolean[G.V()];
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			if (!marked[v]) dfs(G, v);
	}
	
	private void dfs (Digraph G, int v)
	{
		onStack[v] = true;
		marked[v] = true;
		for (int w : G.adj(v))
			if (this.hasCycle()) return;
			else if (!marked[w])
			{ edgeTo[w] = v; dfs(G, w); }
			else if (onStack[w])
			{
				cycle = new Stack<Integer>();
				for (int x = v; x != w; x = edgeTo[x])
					cycle.push(x);
				cycle.push(w);
				cycle.push(v);
			}
		onStack[v] = false;
	}
	public boolean hasCycle()
	{ return cycle != null; }
	
	public Iterable<Integer> cycle()
	{ return cycle; }
	
	public static void main (String[] args) throws FileNotFoundException
	{

		System.setIn(new FileInputStream("StatesCycle.txt"));
		
		// Creates hashtable where every state has unique index
		// Create an array with the key/index reversed from the hashtable
		// Add the edges to the graph
		SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
		Digraph G = new Digraph (50);
		
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
		
		// Checks and printsout if a cycle is found
		DirectedCycle finder = new DirectedCycle(G);
		if (finder.hasCycle())
		{
			StdOut.print("Directed cycle: ");
			for (int v : finder.cycle())
			{
				StdOut.print(states[v] + " ");
			}
			StdOut.println();
		}
		else
		{
			StdOut.println("No directed cycle");
		}
		StdOut.println();
	}

}

/*
 * Directed cycle: PA CT NY PA 
 */
