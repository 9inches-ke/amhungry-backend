package org.amhungry;

import java.time.LocalTime;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "OpenTime rule", description = "Select opening restaurant in current time")
public class OpenTimeRule {
	
	private Restaurant restaurant;
	
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	@Condition
    public boolean isNotOpen() {
//		System.out.println(restaurant.getOpen_time().compareTo(LocalTime.now()) <= 0 && restaurant.getClose_time().compareTo(LocalTime.now()) > 0);
//		System.out.println(restaurant.getOpen_time() + " " + restaurant.getClose_time() + " " + LocalTime.now());
		System.out.println("Opentime Rule");
        return !(restaurant.getOpen_time().compareTo(LocalTime.now()) <= 0 && restaurant.getClose_time().compareTo(LocalTime.now()) > 0);
    }
	
	@Action
	public void removeRestaurant(){
		System.out.println("Opentime Rule - Remove");
		Launcher.removeRestaurant(restaurant);
	}
	
	@Priority
	public int getPriority(){
		return 1;
	}
	
}
