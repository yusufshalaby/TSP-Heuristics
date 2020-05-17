import java.io.*;
import java.util.ArrayList;


public class BasicFunctions {
	public static BufferedReader cin = new BufferedReader ( new InputStreamReader ( System .in));

	public static boolean fileExists(String filename){
		File file = new File (filename);
		return file.exists();
	}
	
	public static double getAverage(ArrayList <Double> D){
		double sum = 0;
		double average;
		for (int i = 0; i < D.size(); i++){
			sum += D.get(i);
		}
		average = sum/D.size();
		return average;
	}
	
	public static double getStDev(ArrayList <Double> D){
		double mean = getAverage(D);
		double variance = 0;
		for (int i = 0; i < D.size(); i++){
			variance += (D.get(i)-mean)*(D.get(i)-mean);
		}
		variance /= D.size()-1;
		return Math.sqrt(variance);
	}
	
	public static double getMin(ArrayList <Double> D){
		double min = Double.MAX_VALUE;
		for (int i = 0; i < D.size(); i++){
			if (D.get(i) < min) min = D.get(i);
		}
		return min;
	}
	
	public static double getMax(ArrayList <Double> D){
		double max = Double.MIN_VALUE;
		for (int i = 0; i < D.size(); i++){
			if (D.get(i) > max) max = D.get(i);
		}
		return max;
	}
	
	public static int getInteger ( String prompt, int LB, int UB) {
		int x = 0;
		boolean valid ;
		boolean infinity = (UB == Integer.MAX_VALUE);
		
		if (infinity){
		do {
	
			valid = true ;
			System.out.print(prompt);
			try{x=Integer.parseInt(cin.readLine());}
		
			catch(NumberFormatException e){
				System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n",LB);
				valid = false;
			}
			catch(IOException e){
				System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n",LB);
				valid = false;
			}
			if(valid && (x<LB||x>UB) ){
				System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n",LB);
				valid = false;
			}
			
			} while(!valid);
			return x;
		}
		else 	{
			do {
				
				valid = true ;
				System.out.print(prompt);
				try{x=Integer.parseInt(cin.readLine());}
				
				catch(NumberFormatException e){
					System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n",LB,UB);
					valid = false;
				}
				catch(IOException e){
					System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n",LB,UB);
					valid = false;
				}
				if(valid && (x<LB||x>UB) ){
					System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n",LB,UB);
					valid = false;
				}
					
				} while(!valid);
				return x;
		}
		}
		
	public static double getDouble ( String prompt, double LB, double UB) {
		double x = 0;
		boolean valid ;
		boolean infinity = (UB == Double.MAX_VALUE);
		
		if (infinity){
			do {
	
				valid = true ;
				System.out.print(prompt);
				try{x=Double.parseDouble(cin.readLine());}
		
				catch(NumberFormatException e){
					System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n",LB);
					valid = false;
				}
				catch(IOException e){
					System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n",LB);
					valid = false;
				}
				if(valid && (x<LB || x>UB) ){
					System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n",LB);
					valid = false;
				}
			
			} while(!valid);
			return x;
			}
	
		else	{
			do {
				
				valid = true ;
				System.out.print(prompt);
				try{x=Double.parseDouble(cin.readLine());}
				
				catch(NumberFormatException e){
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n",LB,UB);
					valid = false;
				}
				catch(IOException e){
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n",LB,UB);
					valid = false;
				}
				if(valid && (x<LB || x>UB) ){
					System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n",LB,UB);
					valid = false;
				}
					
				} while(!valid);
				return x;
				}
		
	}
}


