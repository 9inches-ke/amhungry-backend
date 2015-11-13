package org.amhungry;

import org.easyrules.api.RulesEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	
    public static void main(String[] args) {
    	
    	//Assuming restaurant
    	Restaurant rest_a = new Restaurant("Ja-Ae", 50, 0.4, LocalTime.of(10, 0), LocalTime.of(22, 0), 5, "general");
    	Restaurant rest_b = new Restaurant("Sam", 120, 0.5, LocalTime.of(11, 0), LocalTime.of(22, 0), 3.5, "steak");
    	Restaurant rest_c = new Restaurant("Steak Home", 110, 0.25, LocalTime.of(11, 0), LocalTime.of(22, 0), 4.5, "steak");
    	
    	restaurantList.add(rest_a);
    	restaurantList.add(rest_b);
    	restaurantList.add(rest_c);
    	
    	//Assuming filter from user
    	user_filter.put("price", 70.0);
    	user_filter.put("distance", 0.5); 
    	user_filter.put("vote", 4.0);
    	user_filter.put("type", "steak");
        
        //Check filter
        checkFilter();
        
        //Problem solving
        fireRestaurantList();
        
        calculateRC_List();
        sortArray();
   
        printAllRestaurant();
        
        
        Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
          System.out.println("path");
          c.setAutoCommit(false);
          System.out.println("Opened database successfully");

          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");

          while ( rs.next() ) {
             String  name = rs.getString("name");
             System.out.println( "NAME = " + name );
             System.out.println();
          }    

          stmt = c.createStatement();
          ResultSet rs1 = stmt.executeQuery("SELECT * FROM restaurant_restaurant;");
          while ( rs1.next() ) {
            int id = rs1.getInt("id");
             String  name = rs1.getString("name");
             System.out.println( "ID = " + id );
             System.out.println( "NAME = " + name );
             System.out.println();
          }    c.close();

        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}