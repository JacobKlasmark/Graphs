import edu.princeton.cs.algs4.Bag;

public class Graph 
{
	private static final char NEWLINE = '\n';
	//private static final String NEWLINE = System.getProperty("line.seperator");
	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	// Initialize an empty graph with V vertices and 0 edges
	public Graph (int V)
	{
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++)
		{
			adj[v] = new Bag<Integer>();
		}
	}
	
	// Returns number of vertices 
	public int V()
	{
		return V;
	}
	
	// Returns number of edges
	public int E()
	{
		return E;
	}
	// Checks if the vertex is in the graph
	private void validateVertex (int v)
	{
		if (v < 0 || v >= V)
			throw new IllegalArgumentException ("vertex " + v + " is not between 0 and " + (V-1));
		
	}	
	// adds undirected edge between vertices v and w to this graph
	public void addEdge(int v, int w)
	{
		validateVertex(v);
		validateVertex(w);
		E++;
		adj[v].add(w);
		adj[w].add(v);
		
	}
	
	public Iterable<Integer> adj(int v)
	{
		validateVertex(v);
		return adj[v];
	}
	
	public String toString(String[] states)
	{
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++)
		{
			s.append (states[v] + ": ");
			for (int w : adj[v])
			{
				s.append(states[w] + " ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
	
}


