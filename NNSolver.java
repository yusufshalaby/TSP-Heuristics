import java.util.ArrayList;


public class NNSolver extends TSPSolver {
	public NNSolver(){
		super();
		setName("NN");
	}
	
	
	
	@Override
	public int[] nextNode(Graph G, ArrayList<Integer> visited)
	{
		int size = visited.size();
		int [] nn;
		
		if (size<G.getN()) nn = new int [] {nearestNeighbor(G, visited, visited.get(size-1)), size};
		
		else
		{
		if (G.existsArc(visited.get(size-1),visited.get(0))) 
			nn = new int [] {visited.get(0), size};
		else nn = new int [] {-1, -1};
		}
		return nn;	
	}
	
}
