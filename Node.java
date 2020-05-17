import java.io.IOException;


public class Node {
	final static public int R = 6371;
	private String name ; // node name
	private double lat ; // latitude coordinate
	private double lon ; // longitude coordinate
	
	// constructors
	public Node (){
		this.name = null;
		this.lat = 0;
		this.lon = 0;
	}
	public Node ( String name , double lat , double lon){
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	// setters
	public void setName ( String name ){
		this.name = name;
	}
	public void setLat ( double lat){
		if (lat<-90||lat>90) return;
		this.lat = lat;
	}
	public void setLon ( double lon){
		if (lon<-180||lon>180) return;
		this.lon = lon;
	}
	
	// getters
	public String getName (){
		return this.name;
	}
	public double getLat (){
		return this.lat;
	}
	public double getLon (){
		return this.lon;
	}
	
	public void userEdit () throws IOException // get user info and edit node
	{
		System.out.print("   Name: ");
		setName(BasicFunctions.cin.readLine());
		setLat(BasicFunctions.getDouble("   latitude: ", -90.0, 90.0));
		setLon(BasicFunctions.getDouble("   longitude: ", -180.0,180.0));
	}
	public void print () // print node info as a table row
	{
		String s = "(" + this.lat + "," + this.lon + ")";
		System.out.printf("%19s%19s",this.name,s);
	}
	public static double distance ( Node i, Node j)// calc distance btwn two nodes
	{
		double deltax = Math.toRadians(i.lat - j.lat);
		double deltay = Math.toRadians(i.lon - j.lon);
		double a = Math.pow(Math.sin(deltax/2), 2) + Math.cos(Math.toRadians(i.lat))*Math.cos(Math.toRadians(j.lat))*Math.pow(Math.sin(deltay/2), 2);
		double b = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R*b;
	}
}
