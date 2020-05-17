import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Pro5_shalaby6 {
	
	public static void main(String[] args) throws IOException{
		ArrayList <Graph> graphlist = new ArrayList <Graph> ();
		NNSolver NN = new NNSolver();
		NNFLSolver FL = new NNFLSolver();
		NISolver NI = new NISolver();
		Menu(graphlist,NN,FL,NI);
	}
	
	
	public static void displayMenu () {
		System.out.println("   JAVA TRAVELING SALESMAN PROBLEM V2");
		System.out.println("L - Load graphs from file");
		System.out.println("I - Display graph info");
		System.out.println("C - Clear all graphs");	
		System.out.println("R - Run all algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
	}
	
	public static int menuChoice (String prompt) throws IOException {
		System.out.print(prompt);
		
		int x;
		String str = new String(BasicFunctions.cin.readLine());
		
		if(str.equalsIgnoreCase("Q")) x=0;
		else if(str.equalsIgnoreCase("L")) x=1;
		else if(str.equalsIgnoreCase("I")) x=2;
		else if(str.equalsIgnoreCase("C")) x=3;
		else if(str.equalsIgnoreCase("R")) x=4;
		else if(str.equalsIgnoreCase("D")) x=5;
		else if(str.equalsIgnoreCase("X")) x=6;
		else x=7;
		return x;
	}
	
	public static void Menu(ArrayList<Graph> G, NNSolver NN, NNFLSolver FL, NISolver NI) throws IOException { 
		boolean exit = false;
		boolean graphs = false;
		boolean results = false;
		boolean temp;
		while(!exit){
		displayMenu();
		int menu = menuChoice("\nEnter choice: ");
		switch (menu){
		
		case 0: System.out.println("\nCiao!"); 
		exit = true; 
		break;
		
		case 1: System.out.println(); 
		temp = loadFile(G);
		if (temp) {
			graphs = temp;
			resetAll(NN, FL, NI);
			results = false;
		}
		
		break;
		
		case 2: System.out.println(); 
		if (graphs) {displayGraphs(G);} 
		else System.out.println("ERROR: No graphs have been loaded!\n");
		break;
		
		case 3: System.out.println(); 
		if (graphs) {
			G.clear();
			resetAll(NN, FL, NI);
			graphs = false;
			System.out.println("All graphs cleared.\n");
			
			results = false;
		} 
		else System.out.println("ERROR: No graphs have been loaded!\n");
		break;
		
		case 4: System.out.println(); 
		if (graphs) {
			runAll(G, NN, FL, NI);
			if (NN.hasResults()||FL.hasResults()||NI.hasResults()) results = true;
		}
		else System.out.println("ERROR: No graphs have been loaded!\n");
		break;
		
		case 5: System.out.println(); 
		if (results){
			printAll(NN, FL, NI);
		}
		else System.out.println("ERROR: Results do not exist for all algorithms!\n");
		break;
		
		case 6: System.out.println();
		if (results){
			compare(NN, FL, NI);
		}
		else System.out.println("ERROR: Results do not exist for all algorithms!\n");
		break;
		
		case 7: System.out.println("\nERROR: Invalid menu choice!\n"); 
			}
		}
	}
	
	public static void resetAll(NNSolver NN, NNFLSolver FL, NISolver NI){
		NN.reset();
		FL.reset();
		NI.reset();
	}

	public static void runAll(ArrayList <Graph> G, NNSolver NN, NNFLSolver FL, NISolver NI){
		NN.runAll(G);
		FL.runAll(G);
		NI.runAll(G);
	}
	
	public static void printAll(NNSolver NN, NNFLSolver FL, NISolver NI){
		NN.printAll();
		NN.printStats();
		System.out.println();
		FL.printAll();
		FL.printStats();
		System.out.println();
		NI.printAll();
		NI.printStats();
	}
	
	public static void compare(NNSolver NN, NNFLSolver FL, NISolver NI){
		String costwinner = "NN";
		String timewinner= "NN"; 
		String ratewinner = "NN";
		String winner = "Unclear";
		
		if (FL.avgCost()<NN.avgCost()){
			costwinner = "NN-FL";
		}
		if (NI.avgCost()<NN.avgCost() && NI.avgCost()<FL.avgCost()){
			costwinner = "NI";
		}
		
		if (FL.avgTime()<NN.avgTime()){
			timewinner = "NN-FL";
		}
		if (NI.avgTime()<NN.avgTime() && NI.avgTime()<FL.avgTime()){
			timewinner = "NI";
		}
		
		if (FL.successRate()>NN.successRate()){
			ratewinner = "NN-FL";
		}
		if (NI.successRate()>NN.successRate() && NI.successRate()>FL.successRate()){
			ratewinner = "NI";
		}		
		
		if (costwinner.equals("NN") && timewinner.equals("NN") && ratewinner.equals("NN")){
			winner = "NN";
		}
		if (costwinner.equals("NN-FL") && timewinner.equals("NN-FL") && ratewinner.equals("NN-FL")){
			winner = "NN-FL";
		}
		if (costwinner.equals("NI") && timewinner.equals("NI") && ratewinner.equals("NI")){
			winner = "NI";
		}
		
		System.out.println("------------------------------------------------------------");
		System.out.printf("%20s%19s%21s\n", "Cost (km)", "Comp time (ms)", "Success rate (%)");
		System.out.println("------------------------------------------------------------");
		System.out.printf("NN%18.2f%19.3f%21.1f\n", NN.avgCost(), NN.avgTime(), NN.successRate());
		System.out.printf("NN-FL%15.2f%19.3f%21.1f\n", FL.avgCost(), FL.avgTime(), FL.successRate());
		System.out.printf("NI%18.2f%19.3f%21.1f\n", NI.avgCost(), NI.avgTime(), NI.successRate());
		System.out.println("------------------------------------------------------------");
		System.out.printf("Winner%14s%19s%21s\n", costwinner, timewinner, ratewinner);
		System.out.println("------------------------------------------------------------");
		System.out.println("Overall winner: " + winner + "\n");
	}
	
	public static boolean loadFile(ArrayList<Graph> G) throws IOException{
		System.out.print("Enter file name (0 to cancel): ");
		String filename = BasicFunctions.cin.readLine();
		if (filename.equals("0")){
			System.out.println("\nFile loading process canceled.\n");
			return false;
		}
		if (BasicFunctions.fileExists(filename)==false){
			System.out.println("\nERROR: File not found!\n");
			return false;
		}
		
		else {
			BufferedReader fin = new BufferedReader(new FileReader(filename));
			String line;
			int nnode;
			int total = 0;
			int loaded = 0;
			boolean end = false;
			boolean valid;
			do{
				valid = true;
				Graph newgraph = new Graph();
				do{
					line = fin.readLine();
					if (line==null||line.equals("\n")||line.equals("")) {
						end = true;
						break;
					}
					nnode = Integer.parseInt(line);
					newgraph.init(nnode);
					total++;
					for (int i = 0; i < nnode; i++){
						if (valid){
						line = fin.readLine();
						String [] splitString = line.split(",");
						String name = splitString[0];
						double lat = Double.parseDouble(splitString[1]);
						double lon = Double.parseDouble(splitString[2]);
						Node newnode = new Node(name,lat,lon);
						valid = newgraph.addNode(newnode);
						if (valid==false) loaded++;
						}
						else fin.readLine();
					}
					for (int i = 0; i < nnode-1; i++){
						if (valid) {
						line = fin.readLine();
						String [] splitString = line.split(",");
						for (int j = 0; j < splitString.length; j++){
							int target = Integer.parseInt(splitString[j]);
							newgraph.addArc(i, target-1);
						}
						}
						else fin.readLine();
					}
					break;
				}while(true);
				if (end) break; 
				if (valid) G.add(newgraph);
				line = fin.readLine();
				if (line==null) { break; }
			}while(true);
			fin.close();
			System.out.format("\n%d of %d graphs loaded!\n\n",total-loaded,total);
			return true;
		}
		
	}

	public static void displayGraphs(ArrayList<Graph> G){
		while (true){
		System.out.println("GRAPH SUMMARY");
		String nds = "# nodes";
		String arcs = "# arcs";
		System.out.format("No.%11s%10s\n",nds,arcs);
		System.out.println("------------------------");
		for (int i = 0; i < G.size(); i++){
			System.out.format("%3d%11d%10d\n", i+1,G.get(i).getN(),G.get(i).getM());
		}
		System.out.println();
		int graph = BasicFunctions.getInteger("Enter graph to see details (0 to quit): ",0,G.size());
		if (graph == 0) {System.out.println(); break;}
		System.out.println();
		G.get(graph-1).print();
		System.out.println();
		}
		return;
	}
}
