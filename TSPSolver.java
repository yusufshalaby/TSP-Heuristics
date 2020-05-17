import java.util.ArrayList;


public class TSPSolver {
private ArrayList <int []> solnPath ; // ArrayList *or* array of solution paths
private ArrayList <Double> solnCost ;// ArrayList *or* array of solution costs
private ArrayList <Double> compTime ; // ArrayList *or* array of computation times
private ArrayList <Boolean> solnFound ; // ArrayList *or* array of T/F solns found
private boolean resultsExist ; // whether or not results exist
private String name;


// constructors
public TSPSolver () {
	solnPath = new ArrayList <int[]> ();
	solnCost = new ArrayList <Double> ();
	compTime = new ArrayList <Double> ();
	solnFound = new ArrayList <Boolean> ();
	name = new String ();
}
public TSPSolver ( ArrayList <Graph > G) { 
}

public void runAll (ArrayList <Graph> G){
	init(G);
	for (int i = 0; i <G.size(); i++){
		run(G, i, false);
	}
	for (int i = 0; i < G.size(); i++){
		if (getSolnFound(i)) {
			setHasResults(true);
			break;
		}
	}
	String str = "solver";
	switch(getName()){
	case "NN": str = "Nearest neighbor"; break;
	case "NN-FL": str = "Nearest neighbor first-last"; break;
	case "NI": str = "Node insertion"; break;
	}
	System.out.println(str + " algorithm done.\n");
}

// getters
public int [] getSolnPath ( int i){
	return solnPath.get(i);
}
public double getSolnCost ( int i){
	return solnCost.get(i);
}
public double getCompTime ( int i){
	return compTime.get(i);
}
public boolean getSolnFound ( int i){
	return solnFound.get(i);
}
public boolean hasResults (){
	return resultsExist;
}
public String getName(){ 
	return this.name;
}


// setters
public void setSolnPath ( int i, int [] solnPath ){
	this.solnPath.set(i, solnPath);
}
public void setSolnCost ( int i, double solnCost ){
	this.solnCost.set(i, solnCost);
}
public void setCompTime ( int i, double compTime ){
	this.compTime.set(i, compTime);
}
public void setSolnFound ( int i, boolean solnFound ){
	this.solnFound.set(i, solnFound);
}
public void setHasResults ( boolean b){
	this.resultsExist = b;
}
public void setName(String name){ 
this.name = name;
}

public void init ( ArrayList <Graph > G) // initialize variables and arrays
{
	reset();
	for (int i = 0; i < G.size(); i++){
		int [] temp = new int [G.get(i).getN()+1];
		solnPath.add(temp);
		solnCost.add((double) 0);
		compTime.add((double) 0);
		solnFound.add(false);
	}
}
public void reset () // reset variables and arrays
{
	this.solnPath.clear();
	this.solnCost.clear();
	this.compTime.clear();
	this.solnFound.clear();
	setHasResults(false);
}

public void run( ArrayList <Graph > G, int i, boolean suppressOutput) // run nearest neighbor on Graph i
{
	ArrayList<Integer> visited = new ArrayList<Integer>();
	visited.add(0);
	double cost = 0;
	boolean fail = false;
	long start = System.currentTimeMillis();
	int node, pos;
	int nn[];
	
	while (visited.size() < G.get(i).getN()+1){
		nn = nextNode(G.get(i), visited);
		node = nn[0];
		pos = nn[1];
		if (node == -1) {
			fail = true; 
			break;
			} 
		visited.add(pos, node);
		} ;
		
	long eclipsedtime = System.currentTimeMillis() - start;
	if (fail&&suppressOutput) { 
		setSolnFound(i, false);	
		return;
	}
	if (fail&&!suppressOutput) { 
		setSolnFound(i, false);
		System.out.println("ERROR: " + getName() + " did not find a TSP route for Graph " + (i+1)+"!");
		return; 
	}
	int path [] = new int [visited.size()];
	for (int j = 0; j < visited.size()-1; j++){
	path[j] = visited.get(j)+1;
	cost += G.get(i).getCost(visited.get(j), visited.get(j+1));
}
	setSolnFound(i, true);
	setCompTime(i, eclipsedtime);
	setSolnCost(i, cost);
	path[visited.size()-1] = visited.get(visited.size()-1)+1;
	setSolnPath(i, path);
	
}

public int [] nextNode(Graph G, ArrayList<Integer> visited) // find next node ( return 2D array with [0]= next node , [1]= insertion position )
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

public int nearestNeighbor ( Graph G, ArrayList < Integer > visited, int k ) // find node k's nearest unvisited neighbor
{
	double mincost = Double.MAX_VALUE;
	int nn = -1;
	for (int i = 0; i < G.getN(); i++){
		if (G.existsArc(k, i) && G.getCost(k, i)<mincost && !visited.contains(i)) {
			mincost = G.getCost(k, i);
			nn = i;
		}
	}
	return nn;
}

public void printSingleResult ( int i, boolean rowOnly ) // print results for a single graph
{
	if (rowOnly) {
		String str = Integer.toString(solnPath.get(i)[0]);
		for (int a = 1; a < solnPath.get(i).length; a++){
			str = str.concat("-" + solnPath.get(i)[a]);
		}
		System.out.format("%3d%17.2f%19.3f   %s\n", i+1, getSolnCost(i), getCompTime(i), str);
	}
	else System.out.format("%3d%17s%19s   %s\n", i+1, "-", "-", "-");
}
public void printAll () // print results for all graphs
{
	String str = "solver";
	switch(getName()){
	case "NN": str = "nearest neighbor"; break;
	case "NN-FL": str = "nearest neighbor first-last"; break;
	case "NI": str = "node insertion"; break;
	}
	System.out.println("Detailed results for " + str + ":\n-----------------------------------------------");
	System.out.format("%3s%17s%19s%8s\n", "No.", "Cost (km)", "Comp time (ms)", "Route");
	System.out.println("-----------------------------------------------");
	for (int i = 0; i < solnFound.size(); i++){
		printSingleResult (i,getSolnFound(i));
	}
	System.out.println();
}

public double avgCost(){
	ArrayList <Double> statsSolnCost = new ArrayList <Double> ();
	
	for (int i = 0; i < solnFound.size(); i++){
		if (getSolnFound(i)){
			statsSolnCost.add(getSolnCost(i));
		}
	}
	return BasicFunctions.getAverage(statsSolnCost);
}

public double avgTime(){
	ArrayList <Double> statsCompTime = new ArrayList <Double> ();
	for (int i = 0; i < solnFound.size(); i++){
		if (getSolnFound(i)){
			statsCompTime.add(getCompTime(i));
		}
	}
	return BasicFunctions.getAverage(statsCompTime);
}

public void printStats () // print statistics
{
	ArrayList <Double> statsSolnCost = new ArrayList <Double> ();
	ArrayList <Double> statsCompTime = new ArrayList <Double> ();
	for (int i = 0; i < solnFound.size(); i++){
		if (getSolnFound(i)){
			statsSolnCost.add(getSolnCost(i));
			statsCompTime.add(getCompTime(i));
		}
	}
	String str = "solver";
	switch(getName()){
	case "NN": str = "nearest neighbor"; break;
	case "NN-FL": str = "nearest neighbor first-last"; break;
	case "NI": str = "node insertion"; break;
	}
	System.out.println("Statistical summary for " + str + ":\n---------------------------------------");
	System.out.format("%20s%20s", "Cost (km)", "Comp time (ms)\n");
	System.out.println("---------------------------------------");
	System.out.format("%7s%13.2f%19.3f\n", "Average", BasicFunctions.getAverage(statsSolnCost), BasicFunctions.getAverage(statsCompTime));
	System.out.format("%6s%14.2f%19.3f\n", "St Dev", BasicFunctions.getStDev(statsSolnCost), BasicFunctions.getStDev(statsCompTime));
	System.out.format("%3s%17.2f%19.3f\n", "Min", BasicFunctions.getMin(statsSolnCost), BasicFunctions.getMin(statsCompTime));
	System.out.format("%3s%17.2f%19.3f\n", "Max", BasicFunctions.getMax(statsSolnCost), BasicFunctions.getMax(statsCompTime));
	System.out.format("\nSuccess rate: %.1f%%\n\n",successRate());
}
public double successRate () // calculate success rate
{	
int counter = 0;
	double rate;
	for (int i = 0; i < solnFound.size(); i++){
		if (getSolnFound(i)) counter++;
	}
	rate = 100*((double)counter/(double)solnFound.size());
	return rate;
}

}

