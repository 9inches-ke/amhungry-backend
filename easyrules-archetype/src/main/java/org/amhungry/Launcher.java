package org.amhungry;

import org.easyrules.api.RulesEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

/**
 * Launcher class of the Hello World sample.
 */
public class Launcher {

	private static ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
	private static ArrayList<Restaurant> copyRestaurantList = new ArrayList<Restaurant>();
	//create a rules engine
    private static RulesEngine rulesEngine = aNewRulesEngine().build();
    private static OpenTimeRule openTime = new OpenTimeRule();
    private static DistanceRule distRule = new DistanceRule();
    private static PriceRule priceRule = new PriceRule();
    private static VoteRule voteRule = new VoteRule();
    private static TypeRule typeRule = new TypeRule();
    
    private static RCFormula formula;
    
    //filter from user
    private static HashMap<String, Comparable> user_filter = new HashMap<String, Comparable>();
	
	
	public static void removeRestaurant(Restaurant restaurant){
		restaurantList.remove(restaurant);
	}
	
	private static void calculateRC_List(){
		for(int i = 0; i < restaurantList.size(); i++){
			formula = new RCFormula(restaurantList.get(i));
//			System.out.println("RC: " + formula.getRC());
			if(user_filter.get("distance") != null) {
				formula.setStd_distance((Double) user_filter.get("distance"));
			}
			if(user_filter.get("price") != null) {
				formula.setStd_price((Double) user_filter.get("price"));
			}
			restaurantList.get(i).setRC(formula.getRC());
		}
	}
	
	public static void sortArray(){
		 Collections.sort(restaurantList);
	}
	
	private static void printAllRestaurant(){
		for(int i = 0; i < restaurantList.size(); i++){
			System.out.println(restaurantList.get(i).toString());
//        	System.out.print(restaurantList.get(i).toStringID());
        }
	}
	
	private static void checkFilter(){
        if(user_filter.containsKey("distance")){
        	distRule.setSTD_distance((Double) user_filter.get("distance"));
        }
        if(user_filter.containsKey("price")){
        	priceRule.setSTD_price((Double) user_filter.get("price"));
        	rulesEngine.registerRule(priceRule);
        }
        if(user_filter.containsKey("vote")){
        	voteRule.setFilterVote((Double) user_filter.get("vote"));
        	rulesEngine.registerRule(voteRule);
        }
        if(user_filter.containsKey("type")){
        	typeRule.setFilterType((String) user_filter.get("type"));
        	rulesEngine.registerRule(typeRule);
        }
	}
	
	private static void fireRestaurantList(){
		rulesEngine.registerRule(openTime);
        rulesEngine.registerRule(distRule);
		copyRestaurantList.addAll(restaurantList);
        
        for(Restaurant temp: copyRestaurantList){
        	System.out.println(temp.getName());
        	openTime.setInput(temp);
        	distRule.setInput(temp);
        	priceRule.setInput(temp);
        	if(user_filter.containsKey("vote")){
        		voteRule.setInput(temp);
        	}
        	if(user_filter.containsKey("type")){
        		typeRule.setInput(temp);
        	}
        	rulesEngine.fireRules();
        }
	}
	
	public static double getDistance(double lat1, double lat2, double lng1, double lng2) 
	{
	  double earthRadius = 6371; // KM: use mile here if you want mile result

	  double dLat = toRadian(lat2 - lat1);
	  double dLng = toRadian(lng2 - lng1);

	  double a = Math.pow(Math.sin(dLat/2), 2)+Math.cos(toRadian(lat1))*Math.cos(toRadian(lat2))*Math.pow(Math.sin(dLng/2), 2);

	  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	  return (earthRadius * c); // returns result kilometers
	}

	public static double toRadian(double degrees) 
	{
	  return (degrees * Math.PI) / 180.0d;
	}
	
	public static LocalTime getTime(String time){
		String[] temp = time.split(":");
		return LocalTime.of(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
	}
	
    public static void main(String[] args) {
        //wait for real input vai terminal
    	Scanner scan = new Scanner(System.in);
    	String input = ",100,13.84,100.57,0.5";
//    	input = scan.nextLine();
    	String[] us_input = input.split(",");
    	String type = us_input[0];
    	double price = Double.parseDouble(us_input[1]);
    	double us_x = Double.parseDouble(us_input[2]);
    	double us_y = Double.parseDouble(us_input[3]);
    	double distance = Double.parseDouble(us_input[4]);
    	
    	Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
          c.setAutoCommit(false);

          
          stmt = c.createStatement();
          ResultSet rs1 = stmt.executeQuery("SELECT * FROM restaurant_restaurant;");
          while ( rs1.next() ) {
        	  int id = rs1.getInt("id");
              String  name = rs1.getString("name");
              System.out.println( "ID = " + id );
              System.out.println( "NAME = " + name );
              System.out.println();
        	restaurantList.add(new Restaurant(rs1.getInt("id"),rs1.getString("name"),rs1.getDouble("price"),getDistance(rs1.getFloat("location_x"),us_x,rs1.getFloat("location_y"),us_y),getTime(rs1.getString("open_time")),getTime(rs1.getString("close_time")),5,rs1.getString("type")));
          }    c.close();

        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Operation done successfully");
    	

    	//Dummy restaurant
        Restaurant dummy = new Restaurant(0,"Null_Restaurant", 0, 0, LocalTime.of(0, 0), LocalTime.of(23,59,59), 5, type);
    	restaurantList.add(dummy);

    	
    	//Assuming filter from user
    	user_filter.put("price", price);
    	user_filter.put("distance", distance); 
    	user_filter.put("vote", 0.0);
    	if(!type.equals(""))user_filter.put("type", type);
 
        //Check filter
        checkFilter();
        
        //Problem solving
        fireRestaurantList();
        
        calculateRC_List();
        sortArray();
        System.out.println(input);
        System.out.println(LocalTime.now());
//        System.out.print("|-result-| = ");
        printAllRestaurant();
        
    }
}