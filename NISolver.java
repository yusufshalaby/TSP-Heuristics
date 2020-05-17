import java.util.ArrayList;


public class NISolver extends TSPSolver {
	public NISolver(){
		super();
		setName("NI");
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
			int temp;
			int node = -1;
			int pos = -1;
			double cost = Double.MAX_VALUE;
			
				
			for (int i = 0; i < size; i++){
				temp = nearestNeighbor(G, visited, visited.get(i));
					if (temp != -1 && G.getCost(visited.get(i), temp) < cost){
					node = temp;
					if (i == 0) pos = i;
					else pos = i + 1;
					cost = G.getCost(visited.get(i), node);
				}
			}		
			nn = new int [] {node, pos};
		}

		return nn;
	}
	
	@Override
	public int nearestNeighbor ( Graph G, ArrayList < Integer > visited, int k ) // find node k's nearest unvisited neighbor
	{
		double mincost = Double.MAX_VALUE;
		int nn = -1;
		for (int i = 0; i < G.getN(); i++){
			if (G.existsArc(k, i) && G.getCost(k, i)<mincost && canBeInserted(G, visited, visited.indexOf(k)+1, i) &&!visited.contains(i)) {
				mincost = G.getCost(k, i);
				nn = i;
			}
		}
		return nn;
	}
	
	// check if node k can be inserted at position i
	public boolean canBeInserted ( Graph G, ArrayList < Integer > visited , int i, int k){
		if (i == 1 || i == visited.size()) return true;
		else return G.existsArc(k,  visited.get(i));
	}
	
}
