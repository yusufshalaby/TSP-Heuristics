import java.util.ArrayList;


public class NNFLSolver extends TSPSolver{
	public NNFLSolver(){
		super();
		setName("NN-FL");
	}
	
	
	@Override
	public int[] nextNode(Graph G, ArrayList<Integer> visited){
		int [] nn;
		int size = visited.size();
		if (size==G.getN())
		{
		if (G.existsArc(visited.get(size-1),visited.get(0)))
			nn = new int [] {visited.get(0), size};
		else nn = new int [] {-1, -1};
		}
		
		else{
			
			int end = nearestNeighbor(G, visited, visited.get(size-1));
			int front = nearestNeighbor(G, visited, visited.get(0));
			if (end == -1) nn = new int [] {front, 0};
			else if (front == -1) nn = new int [] {end, size};
			else if (end ==-1 && front == -1) nn = new int [] {-1, -1};
			else if ( G.getCost(front, visited.get(0)) < G.getCost(visited.get(size-1), end)){
				nn = new int [] {front, 0};	
			}
			else nn = new int [] {end, size};
		}
		return nn;
	}
}
