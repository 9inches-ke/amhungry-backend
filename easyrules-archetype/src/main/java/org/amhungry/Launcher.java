package org.amhungry;

import org.easyrules.api.RulesEngine;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

/**
 * Launcher class of the Hello World sample.
 */
public class Launcher {

	private static ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
	
	public static void add(Restaurant restaurant){
		resultList.add(restaurant);
	}
	
    public static void main(String[] args) {
    	
    	Restaurant rest_a = new Restaurant("Ja-Ae", 50, 0.4, LocalTime.of(10, 0), LocalTime.of(22, 0));
    	Restaurant rest_b = new Restaurant("Sam", 120, 0.5, LocalTime.of(16, 0), LocalTime.of(22, 0));
    	Restaurant rest_c = new Restaurant("Steak Home", 110, 0.25, LocalTime.of(18, 0), LocalTime.of(22, 0));
    	
    	ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
    	restaurantList.add(rest_a);
    	restaurantList.add(rest_b);
    	restaurantList.add(rest_c);
    	
    	//create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine().build();
        OpenTimeRule openTime = new OpenTimeRule();
        rulesEngine.registerRule(openTime);
        
        for(int i = 0; i < restaurantList.size(); i++){
        	openTime.setInput(restaurantList.get(i));
        	rulesEngine.fireRules();
        }
    	
        for(int i = 0; i < resultList.size(); i++){
        	System.out.println(resultList.get(i).getName());
        }
        
    	
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Are you a friend of duke? [yes/no]:");
//        String input = scanner.nextLine();
//
//        /**
//         * Declare the rule
//         */
//        HelloWorldRule helloWorldRule = new HelloWorldRule();
//
//        /**
//         * Set business data to operate on
//         */
//        helloWorldRule.setInput(input.trim());
//
//        /**
//         * Create a rules engine and register the business rule
//         */
//        RulesEngine rulesEngine = aNewRulesEngine().build();
//
//        rulesEngine.registerRule(helloWorldRule);
//
//        /**
//         * Fire rules
//         */
//        rulesEngine.fireRules();

    }
}