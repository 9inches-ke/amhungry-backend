package org.amhungry;

import java.time.LocalTime;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Distance rule", description = "Select restaurant in distance")

public class DistanceRule {
	private Restaurant restaurant;
	
	public void setInput(Restaurant restaurant){
		this.restaurant = restaurant;
	}
	
	@Condition
    public boolean isNear() {
        return restaurant.getDistance() < 1;
    }
	
//	@Action
//	public void addRestaurant(){
//		Launcher.add(restaurant);
//	}
}
