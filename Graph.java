import java.util.ArrayList;
//import java.util.regex.Pattern;


public class Graph {
	private int n; // number of nodes
	private int m; // number of arcs
	private ArrayList<Node> node = new ArrayList<Node>() ; // ArrayList *or* array of nodes
	private boolean [][] A; // adjacency matrix
	private double [][] C; // cost matrix

	// constructors
	public Graph (){	
	setN(0);
	setM(0);
	init(0);
	}
	public Graph ( int n){
	setN(n);
	setM(0);
	init(n);
	}
	
	
	// setters
	public void setN ( int n){
		this.n = n;
	}
	public void setM ( int m){
		this.m = m;
	}
	public void setArc ( int i, int j, boolean b){
		this.A[i][j] = b;
		this.A[j][i] = b;
	}
	public void setCost ( int i, int j, double c){
		this.C[i][j] = c;
		this.C[j][i] = c;
	}
	
	// getters
	public int getN (){
		return this.n;
	}
	public int getM (){
		return this.m;
	}
	public boolean getArc ( int i, int j){
		return this.A[i][j];
	}
	public double getCost ( int i, int j){
		return this.C[i][j];
	}
	public Node getNode ( int i){
		return node.get(i);
	}
	
	public void init ( int n) // initialize values and arrays
	{
		this.node.clear();
		this.n = n;
		m = 0;
		
		A = new boolean [n][n];
		C = new double [n][n];
		for (int i = 0; i < n; i++){
			for (int k = 0; k < n; k++){
				A[i][k] = false;
				C[i][k] = 0;
			}
		}
	}
	public void reset () // reset the graph
	{
		 init(0);
	}
	
	public boolean existsGraph() // tests for graph existence
	{
		boolean exist = true;
		if (this.n==0) exist = false;
		return exist;
	}
	
	public boolean existsArc ( int i, int j) // check if arc exists
	{
		return this.A[i][j];
	}
	
	public boolean existsNode ( Node t) // check if node exists
	{
		boolean exists = false;
		for (Node a : node){
			if ( (t.getName().equals(a.getName())) || ( (t.getLat()==a.getLat()) && (t.getLon()==a.getLon()) ) ){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	public boolean addArc ( int i, int j) // add an arc , return T/F success
	{
		if (existsArc(i,j)){
			return false;
		}
		setArc(i,j,true); 
		setCost(i,j,Node.distance(node.get(i),node.get(j)));
		this.m++;
		setM(this.m);
		return true;
	}
	
	
	public void removeArc ( int k) // remove an arc
	{
		int counter = 0;
		boolean removed = false;
		for (int i = 0; i < this.n; i++){
			for (int j = i; j < this.n; j++){
				if (A[i][j] == true){
					counter++;
					if (counter == k){
						setArc(i,j,false);
						setCost(i,j,0);
						this.m--;
						setM(this.m);
						removed = true;
					}
				}
				if (removed) break;
			}
			if (removed) break;
		}		
		System.out.println("\nArc " + (k) + " removed!\n\n");
		
	}
	
	
	public boolean addNode ( Node t) // add a node
	{
		if (Math.abs(t.getLat())>90||Math.abs(t.getLon())>180){
			return false;
		}
		if (existsNode(t)){
				return false;
		
		}
		
		node.add(t);
		setN(node.size());
		return true;
	}

	public void print () // print all graph info
	{
		System.out.println("Number of nodes: " + this.n);
		System.out.println("Number of arcs: " + this.m + "\n");
		printNodes();
		printArcs();
	}
	
	public void printNodes () // print node list
	{
		System.out.println("NODE LIST");
		System.out.printf("%3s%19s%19s\n","No.","Name","Coordinates");
		System.out.print("-----------------------------------------\n");
		for (int i=1; i<this.n+1; i++){
			System.out.printf("%3d",i);
			node.get(i-1).print(); System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	public void printArcs () // print arc list
	{
		String s;
		int counter = 0;
		System.out.println("ARC LIST");
		System.out.printf("%3s%10s%15s\n","No.","Cities","Distance");
		System.out.print("----------------------------\n");
		
		for (int i = 0; i < this.n; i++){
			for (int j = i; j < this.n; j++){
				if (A[i][j] == true){
					counter++;
					s = (i+1) + "-" + (j+1);
					System.out.format("%3d%10s%15.2f\n",counter, s, C[i][j]);	
				}
			}
		}
			System.out.print("\n");
	}

	public boolean checkPath ( int [] P) // check feasibility of path P
	{
		int a = 0;
		int b = 0;
		int route = P.length;
		boolean flag1 = false; 
		boolean flag2 = false; 
		boolean flag3 = false;
		if (P[0] != P [route-1]) flag1 = true;
		for (int i = 1; i < route-1; i++){
			for (int j = 0; j < i; j++){
				if (P[i] == P[j]) flag2 = true; 
			}
		}
		for (int i = 1; i <= route-1; i++){
			if (existsArc(P[i-1]-1, P[i]-1) == false) {
				flag3 = true;
				a = P[i-1];
				b = P[i];
				break;
			}
		}
		if (flag1 == true) {System.out.print("\nERROR: Start and end cities must be the same!\n\n"); return true;}
		else if (flag2 == true) {System.out.print("\nERROR: Cities cannot be visited more than once!\nERROR: Not all cities are visited!\n\n"); return true;}
		else if (flag3 == true) {System.out.print("\nERROR: Arc " + a + "-" + b + " does not exist!\n\n"); return true;}
		else return false;
	}
	
	public double pathCost ( int [] P) // calculate cost of path P
	{
		int route = P.length;
		double cost = 0;
		for (int i = 1; i < route; i++){
			cost += getCost(P[i-1]-1,P[i]-1);
		}
		System.out.format("\nThe total distance traveled is %.2f km.\n\n", cost);
		return cost;
	}
}
