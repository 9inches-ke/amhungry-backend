package org.amhungry;

import org.easyrules.api.RulesEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
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
    
    //filter from user
    private static HashMap<String, Comparable> user_filter = new HashMap<String, Comparable>();
	
	
	public static void removeRestaurant(Restaurant restaurant){
		restaurantList.remove(restaurant);
	}
	
	private static void calculateRC_List(){
		for(int i = 0; i < restaurantList.size(); i++){
			RCFormula formula = new RCFormula(restaurantList.get(i));
//			System.out.println("RC: " + formula.getRC());
			restaurantList.get(i).setRC(formula.getRC());
		}
	}
	
	public static void sortArray(){
		ArrayList<Restaurant> tempList = new ArrayList<Restaurant>();
		tempList.add(restaurantList.get(0));
		for(Restaurant a : restaurantList){
			for(int i=0;i<tempList.size();i++){
				if(tempList.get(i).getRC()<a.getRC()){
					tempList.add(i, a);
					break;
				}
			}
		}
		restaurantList = tempList;
	}
	
	private static void printAllRestaurant(){
		for(int i = 0; i < restaurantList.size(); i++){
        	System.out.println(restaurantList.get(i).toString());
        }
	}
	
	private static void checkFilter(){
		if(user_filter.containsKey("price")){
        	Standard_Value.setSTD_price((Double) user_filter.get("price"));
        }
        if(user_filter.containsKey("distance")){
        	Standard_Value.setSTD_distance((Double) user_filter.get("distance"));
        }
        if(user_filter.containsKey("vote")){
        	voteRule.setFilterVote((Double) user_filter.get("vote"));
        	rulesEngine.registerRule(voteRule);
        }
        if(user_filter.containsKey("type")){
        	Standard_Value.setFilter_type((String) user_filter.get("type"));
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
	
	public static double getDistance(double x1,double x2,double y1,double y2){
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	public static LocalTime getTime(String time){
		String[] temp = time.split(":");
		return LocalTime.of(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
	}
	
    public static void main(String[] args) {
        //wait for real input vai terminal
    	String input = "Jae,seafood,100,13,100";
    	String[] us_input = input.split(",");
    	String name = us_input[0];
    	String type = us_input[1];
    	int price = Integer.parseInt(us_input[2]);
    	double us_x = Double.parseDouble(us_input[3]);
    	double us_y = Double.parseDouble(us_input[4]);
    	
    	Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
          c.setAutoCommit(false);

          
          stmt = c.createStatement();
          ResultSet rs1 = stmt.executeQuery("SELECT * FROM restaurant_restaurant;");
          while ( rs1.next() ) {
        	restaurantList.add(new Restaurant(rs1.getInt("id"),rs1.getString("name"),rs1.getDouble("price"),getDistance(rs1.getFloat("location_x"),us_x,rs1.getFloat("location_y"),us_y),getTime(rs1.getString("open_time")),getTime(rs1.getString("close_time")),5,rs1.getString("type")));
          }    c.close();

        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Operation done successfully");
    	
    	Scanner scan = new Scanner(System.in);
    	double price,vote,distance;
    	String type;
    	//Assuming restaurant
    	Restaurant rest_a = new Restaurant(1,"Ja-Ae", 50, 0.4, LocalTime.of(10, 0), LocalTime.of(22, 0), 5, "general");
    	Restaurant rest_b = new Restaurant(2,"Sam", 120, 0.5, LocalTime.of(11, 0), LocalTime.of(22, 0), 3.5, "steak");
    	Restaurant rest_c = new Restaurant(3,"Steak Home", 110, 0.25, LocalTime.of(11, 0), LocalTime.of(22, 0), 4.5, "steak");
    	
    	restaurantList.add(rest_a);
    	restaurantList.add(rest_b);
    	restaurantList.add(rest_c);
    	
    	//Scan
    	System.out.print("Price: ");
    	price = scan.nextDouble();
    	System.out.print("Distance: ");
    	distance = scan.nextDouble();
    	System.out.print("Vote: ");
    	vote = scan.nextDouble();
    	System.out.print("Type: ");
    	type = scan.next();
    	
    	/*System.out.println("Price: "+price);
    	System.out.println("Distance: "+distance);
    	System.out.println("Vote: "+vote);
    	System.out.println("Type: "+type);*/
    	
    	//Assuming filter from user
    	user_filter.put("price", price);
    	user_filter.put("distance", distance); 
    	user_filter.put("vote", vote);
    	user_filter.put("type", type);
        
        //Check filter
        checkFilter();
        
        //Problem solving
        fireRestaurantList();
        
        calculateRC_List();
        sortArray();
   
        printAllRestaurant();
        
    }
}