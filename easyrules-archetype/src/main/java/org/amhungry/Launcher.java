package org.amhungry;

import org.easyrules.api.RulesEngine;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.time.LocalTime;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

/**
 * Launcher class of the Hello World sample.
 */
public class Launcher {

	private static ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
	private static ArrayList<Restaurant> copyRestaurantList = new ArrayList<Restaurant>();
	
	
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
	
	
    public static void main(String[] args) {
    	
    	//Assuming restaurant
    	Restaurant rest_a = new Restaurant("Ja-Ae", 50, 0.4, LocalTime.of(10, 0), LocalTime.of(22, 0), 5, "General");
    	Restaurant rest_b = new Restaurant("Sam", 120, 0.5, LocalTime.of(11, 0), LocalTime.of(22, 0), 3.5, "Steak");
    	Restaurant rest_c = new Restaurant("Steak Home", 110, 0.25, LocalTime.of(11, 0), LocalTime.of(22, 0), 4.5, "Steak");
    	
    	//Filter from user
    	HashMap<String, Double> user_filter = new HashMap<String, Double>();
    	user_filter.put("price", 70.0);
    	user_filter.put("distance", 0.5); 
//    	user_filter.put("vote", 4.0);
    	
    	restaurantList.add(rest_a);
    	restaurantList.add(rest_b);
    	restaurantList.add(rest_c);
    	
    	//create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine().build();
        OpenTimeRule openTime = new OpenTimeRule();
        DistanceRule distRule = new DistanceRule();
        PriceRule priceRule = new PriceRule();
        VoteRule voteRule = new VoteRule();
        
        rulesEngine.registerRule(openTime);
        rulesEngine.registerRule(distRule);
        
        //Check filter
        if(user_filter.containsKey("price")){
        	priceRule.setSTD_price(user_filter.get("price"));
        }
        if(user_filter.containsKey("distance")){
        	distRule.setSTD_distance(user_filter.get("distance"));
        }
        if(user_filter.containsKey("vote")){
        	voteRule.setFilterVote(user_filter.get("vote"));
        	rulesEngine.registerRule(voteRule);
        }
        
        copyRestaurantList.addAll(restaurantList);
        
        for(Restaurant temp: copyRestaurantList){
        	System.out.println(temp.getName());
        	openTime.setInput(temp);
        	distRule.setInput(temp);
        	priceRule.setInput(temp);
        	if(user_filter.containsKey("vote")){
        		voteRule.setInput(temp);
        	}
        	rulesEngine.fireRules();
        }
        
        calculateRC_List();
        sortArray();
        
        for(int i = 0; i < restaurantList.size(); i++){
        	System.out.println(restaurantList.get(i).toString());
        }

    }
}