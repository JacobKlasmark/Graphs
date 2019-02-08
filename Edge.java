

public class Edge implements Comparable<Edge>
{
	private final int v;
	private final int w;
	private final double weight;
	
	public Edge (int v, int w, double weight)
	{
		this.v = v;
		this.w = w;
		this.weight = weight;		
	}
	
	public double weight()
	{return weight; }
	
	public int either()
	{ return v; }
	
	// Returns endpoint of this edge
	public int other (int vertex)
	{
		if (vertex == v) return w;
		else if (vertex == w) return v;
		else throw new IllegalArgumentException("Illegal endpoint");
	}
	
	// Compares which edge is "heaviest"
	public int compareTo(Edge that)
	{
		if (this.weight() < that.weight()) return -1;
		else if (this.weight() > that.weight()) return +1;
		else return 0;
	}
	
	public String toString(String[] states)
	{ return String.format("%s-%s %.2f",  states[v], states[w], weight); }

}
